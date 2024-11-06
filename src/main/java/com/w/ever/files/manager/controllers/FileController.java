package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.responses.ApiResponse;
import com.w.ever.files.manager.responses.SuccessApiResponse;
import com.w.ever.files.manager.services.FileService;
import com.w.ever.files.manager.services.NotificationsService;
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
    public ApiResponse file(@PathVariable Integer id) {
        FileModel fileModel = fileService.getFile(id);

        return new SuccessApiResponse(fileModel);
    }

    @PostMapping("/file")
    public ApiResponse uploadFile(@RequestBody FileModel file) {
        FileModel fileModel = fileService.createFile(file);
        return new SuccessApiResponse(fileModel);
    }

    @PostMapping("/file/{id}")
    public ApiResponse uploadFile(@RequestBody FileModel file, @PathVariable Integer id) {
        FileModel fileModel = fileService.updateFile(file, id);
        return new SuccessApiResponse(fileModel);
    }

    @DeleteMapping("/file/{id}")
    public void deleteFile(@PathVariable String id) {
        fileService.delete(id);
    }
}
