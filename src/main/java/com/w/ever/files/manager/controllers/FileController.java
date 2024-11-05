package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.models.FileModel;
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
    public FileModel file(@PathVariable Integer id) {
        return fileService.getFile(id);
    }

    @PostMapping("/file")
    public FileModel uploadFile(@RequestBody FileModel file) {
        return fileService.createFile(file);
    }

    @PostMapping("/file/{id}")
    public FileModel uploadFile(@RequestBody FileModel file, @PathVariable Integer id) {
        return fileService.updateFile(file, id);
    }

    @DeleteMapping("/file/{id}")
    public void deleteFile(@PathVariable String id) {
        fileService.delete(id);
    }
}
