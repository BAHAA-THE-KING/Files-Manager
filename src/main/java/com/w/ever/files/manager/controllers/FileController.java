package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.services.FileService;
import com.w.ever.files.manager.services.NotificationsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<FileModel> file(@PathVariable Integer id) {
        FileModel data = fileService.getFile(id);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<FileModel> uploadFile(@RequestBody FileModel file) {
        FileModel data = fileService.createFile(file);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @PostMapping("/file/{id}")
    public ResponseEntity<FileModel> uploadFile(@RequestBody FileModel file, @PathVariable Integer id) {
        FileModel data = fileService.updateFile(file, id);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @DeleteMapping("/file/{id}")
    public void deleteFile(@PathVariable String id) {
        fileService.delete(id);
    }
}
