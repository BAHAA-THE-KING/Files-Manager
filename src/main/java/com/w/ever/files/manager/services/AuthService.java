package com.w.ever.files.manager.services;

import com.w.ever.files.manager.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
