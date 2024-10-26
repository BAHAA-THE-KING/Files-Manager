package com.w.ever.files.manager.services;

import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.repositories.FileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileModel getFile(Integer id) {
        Optional<FileModel> fileModel = fileRepository.findById(id);
        return fileModel.orElse(null);
    }

    public FileModel createFile(FileModel file) {
        return fileRepository.save(file);
    }

    public FileModel updateFile(FileModel file, Integer id) {
        Optional<FileModel> fileModel = fileRepository.findById(id);
        if (fileModel.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        return fileRepository.save(file);
    }

    public void delete(String id) {
    }
}
