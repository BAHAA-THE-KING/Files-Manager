package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.dto.files.CreateFileRequestDTO;
import com.w.ever.files.manager.dto.files.CreateFolderRequestDTO;
import com.w.ever.files.manager.dto.files.ReserveFilesDTO;
import com.w.ever.files.manager.models.CheckInModel;
import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.responses.SuccessResponse;
import com.w.ever.files.manager.services.FileService;
import com.w.ever.files.manager.services.NotificationsService;
import com.w.ever.files.manager.services.StorageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
public class FileController {
    private final FileService fileService;
    private final StorageService storageService;
    private final NotificationsService notificationsService;

    public FileController(FileService fileService, StorageService storageService, NotificationsService notificationsService) {
        this.fileService = fileService;
        this.storageService = storageService;
        this.notificationsService = notificationsService;
    }

    @PostMapping("group/file-request")
    public ResponseEntity createFileRequest(@Valid @ModelAttribute CreateFileRequestDTO requestData) throws IOException {
        MultipartFile file = requestData.getFile();

        if (file.isEmpty()) {
            throw new BadRequestException("The file is empty.");
        }
        String newPath = storageService.storeFile(file);
        FileModel fileModel = fileService.createFile(requestData.getGroupId(), requestData.getPath(), newPath, requestData.getFile().getOriginalFilename());

        /* TODO: Send Notification */
        return new SuccessResponse(fileModel);
    }

    @PostMapping("group/folders")
    public ResponseEntity createFolder(@Valid @RequestBody CreateFolderRequestDTO requestData) throws BadRequestException {
        FileModel fileModel = fileService.createFolder(requestData.getGroupId(), requestData.getPath());
        return new SuccessResponse(fileModel);
    }

    @GetMapping("group/{groupId}/file-requests/{userId}")
    public ResponseEntity getFileRequestsForGroupAndUser(@PathVariable @NotNull Integer groupId, @PathVariable @NotNull Integer userId) {
        List<FileModel> requests = fileService.getFileRequestsForGroupAndUser(groupId, userId);
        return new SuccessResponse(requests);
    }

    @GetMapping("group/{groupId}/file-requests")
    public ResponseEntity getFileRequestsForGroup(@PathVariable Integer groupId) {
        List<FileModel> requests = fileService.getFileRequestsForGroup(groupId);
        return new SuccessResponse(requests);
    }

    @GetMapping("user/{userId}/file-requests")
    public ResponseEntity getFileRequestsForUser(@PathVariable Integer userId) {
        List<FileModel> requests = fileService.getFileRequestsForUser(userId);
        return new SuccessResponse(requests);
    }

    @GetMapping("file-requests/{fileRequestId}")
    public ResponseEntity getFileRequest(@PathVariable Integer fileRequestId) throws BadRequestException {
        FileModel fileRequest = fileService.getFileRequest(fileRequestId);
        return new SuccessResponse(fileRequest);
    }

    @PostMapping("file-requests/{fileRequestId}")
    public ResponseEntity acceptFileRequest(@PathVariable Integer fileRequestId) throws BadRequestException {
        FileModel acceptedFile = fileService.acceptFileRequest(fileRequestId);
        /* TODO: Send a notification */
        return new SuccessResponse(acceptedFile);
    }

    @PostMapping("check-in")
    public ResponseEntity reserveFiles(@Valid @RequestBody ReserveFilesDTO requestData) throws BadRequestException {
        List<Integer> filesIds = requestData.getFilesIds();
        List<CheckInModel> checkIns = fileService.reserveFiles(filesIds);
        return new SuccessResponse(checkIns);
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity getFile(@PathVariable String filename) throws BadRequestException, MalformedURLException {
        Resource resource = fileService.getFile(filename);
        if (!resource.exists()) throw new BadRequestException("File not found");

        // Respond with the file
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
