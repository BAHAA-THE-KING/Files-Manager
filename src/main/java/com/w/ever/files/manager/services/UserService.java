package com.w.ever.files.manager.services;

import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getProfile(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserModel register(UserModel user) {
        return userRepository.save(user);
    }

    public UserModel updateFile(UserModel file, Integer id) {
        Optional<UserModel> existingFileModelOpt = userRepository.findById(id);
        if (existingFileModelOpt.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        // Get the existing entity from the database
        UserModel existingFileModel = existingFileModelOpt.get();

        // Update the fields of the existing entity
        existingFileModel.setName(file.getName());
        existingFileModel.setPath(file.getPath());
        existingFileModel.setParent(file.getParent()); // Handle parent carefully

        // Save the updated entity
        return userRepository.save(existingFileModel);

    }

    public void delete(String id) {
    }
}
