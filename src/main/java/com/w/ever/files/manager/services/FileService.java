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
        if (file.getParent() != null && file.getParent().getId() != null) {
            // Fetch the parent entity from the database to make sure it is managed
            Optional<FileModel> parent = fileRepository.findById(file.getParent().getId());
            if (parent.isPresent()) {
                file.setParent(parent.get()); // Set the managed parent entity
            } else {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Parent entity not found");
            }
        }

        // Now save the file entity, with the parent properly attached
        return fileRepository.save(file);

    }

    public FileModel updateFile(FileModel file, Integer id) {
        Optional<FileModel> existingFileModelOpt = fileRepository.findById(id);
        if (existingFileModelOpt.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        // Get the existing entity from the database
        FileModel existingFileModel = existingFileModelOpt.get();

        // Update the fields of the existing entity
        existingFileModel.setName(file.getName());
        existingFileModel.setPath(file.getPath());
        existingFileModel.setParent(file.getParent()); // Handle parent carefully

        // Save the updated entity
        return fileRepository.save(existingFileModel);

    }

    public void delete(String id) {
    }
}
