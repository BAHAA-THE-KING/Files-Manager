package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.responses.SuccessResponse;
import com.w.ever.files.manager.services.FileService;
import com.w.ever.files.manager.services.NotificationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FileController {
    private final FileService fileService;
    private final NotificationsService notificationsService;

    public FileController(FileService fileService, NotificationsService notificationsService) {
        this.fileService = fileService;
        this.notificationsService = notificationsService;
    }

    @GetMapping("/file/{id}")
    public ResponseEntity file(@PathVariable Integer id) {
        FileModel fileModel = fileService.getFile(id);

        return new SuccessResponse(fileModel);
    }

    @PostMapping("/file")
    public ResponseEntity uploadFile(@RequestBody FileModel file) {
        FileModel fileModel = fileService.createFile(file);
        return new SuccessResponse(fileModel);
    }

    @PostMapping("/file/{id}")
    public ResponseEntity uploadFile(@RequestBody FileModel file, @PathVariable Integer id) {
        FileModel fileModel = fileService.updateFile(file, id);
        return new SuccessResponse(fileModel);
    }

    @DeleteMapping("/file/{id}")
    public void deleteFile(@PathVariable String id) {
        fileService.delete(id);
    }
}
