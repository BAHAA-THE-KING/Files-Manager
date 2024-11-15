package com.w.ever.files.manager.services;

import com.w.ever.files.manager.dto.RegisterDTO;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getProfile(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserModel register(RegisterDTO userData) {
        UserModel user = userRepository.save(new UserModel(userData.getName(), userData.getUsername(), userData.getEmail(), userData.getPassword()));
        /* TODO: Give Him A Token */
        return user;
    }

    public void delete(String id) {
    }
}
