package com.w.ever.files.manager.services;

import com.w.ever.files.manager.models.FileModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileService {
    public FileModel get(String id) {
        return new FileModel(id, "", "");
    }

    public FileModel createFile(FileModel file) {
        file.setId(UUID.randomUUID().toString());

        return file;
    }

    public FileModel createFile(FileModel file, String id) {
        return file;
    }

    public void delete(String id) {
    }
}
