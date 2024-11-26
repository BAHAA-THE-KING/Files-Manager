package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.dto.files.CreateFileRequestDTO;
import com.w.ever.files.manager.dto.files.CreateFolderRequestDTO;
import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.responses.SuccessResponse;
import com.w.ever.files.manager.services.FileService;
import com.w.ever.files.manager.services.NotificationsService;
import com.w.ever.files.manager.services.StorageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
}
