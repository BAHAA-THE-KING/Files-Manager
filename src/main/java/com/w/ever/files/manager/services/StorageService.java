package com.w.ever.files.manager.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {
    // Set the directory for storing uploaded files
    private final Path rootLocation = Paths.get("src/main/resources/static/uploads");

    @Transactional
    public String storeFile(MultipartFile file) throws IOException {
        // Make sure the directory exists
        Files.createDirectories(rootLocation);

        // Extract the file extension
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // Generate a unique file name
        String uniqueFileName = UUID.randomUUID() + extension;

        // Create the destination file path
        Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFileName)).normalize().toAbsolutePath();

        // Check for directory traversal attack
        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new IOException("Cannot store file outside current directory.");
        }

        // Store the file in the static directory
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }

        // Return the path where the file is stored (relative URL for serving)
        return "/uploads/" + uniqueFileName;
    }
}
