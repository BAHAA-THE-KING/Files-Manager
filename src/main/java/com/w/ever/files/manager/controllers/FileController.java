package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.dto.files.CreateFileRequestDTO;
import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.responses.SuccessResponse;
import com.w.ever.files.manager.services.FileService;
import com.w.ever.files.manager.services.NotificationsService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
    private final FileService fileService;
    private final NotificationsService notificationsService;

    public FileController(FileService fileService, NotificationsService notificationsService) {
        this.fileService = fileService;
        this.notificationsService = notificationsService;
    }

    @PostMapping("group/file-requests")
    public ResponseEntity createFileRequest(@Valid @RequestBody CreateFileRequestDTO requestData) throws BadRequestException {
        FileModel fileModel = fileService.createFile(requestData);
        /* TODO: Send Notification */
        return new SuccessResponse(fileModel);
    }
}
