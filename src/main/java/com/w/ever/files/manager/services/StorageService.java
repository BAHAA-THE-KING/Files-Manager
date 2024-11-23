package com.w.ever.files.manager.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    private final Path rootLocation = new File("").toPath();

    public String storeFile(MultipartFile file) throws IOException {
        Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
//            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
        // This is a security check
//                throw new StorageException(
//                        "Cannot store file outside current directory.");
//            }
        InputStream inputStream = file.getInputStream();
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

        return destinationFile.toString();
    }
}
