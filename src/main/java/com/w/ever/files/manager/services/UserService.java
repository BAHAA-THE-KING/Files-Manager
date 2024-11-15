package com.w.ever.files.manager.services;

import com.w.ever.files.manager.dto.RegisterDTO;
import com.w.ever.files.manager.dto.UpdateUserDTO;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.UserRepository;
import org.apache.coyote.BadRequestException;
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

    public UserModel updateUser(Integer id, UpdateUserDTO userData) throws BadRequestException {
        UserModel user = getProfile(id);
        if (user == null) return null;

        if (userData.getName() != null) {
            user.setName(userData.getName());
        }
        if (userData.getUsername() != null) {
            String username = userData.getUsername();
            if (isUsernameUnique(username, id)) {
                user.setUsername(username);
            } else {
                throw new BadRequestException("Username is used");
            }
        }
        if (userData.getEmail() != null) {
            String email = userData.getEmail();
            System.out.println(email);
            if (isEmailUnique(email, id)) {
                user.setEmail(email);
            } else {
                throw new BadRequestException("Email is used");
            }
        }
        if (userData.getPassword() != null) {
            user.setPassword(userData.getPassword());
        }

        return userRepository.save(user);
    }

    public void deleteUser(Integer id) throws BadRequestException {
        if (!userRepository.exists(id)) {
            throw new BadRequestException("User doesn't exist.");
        }
        userRepository.deleteById(id);
    }

    public boolean isEmailUnique(String email, Integer id) {
        if (email == null) return true;
        return !userRepository.existsByEmailAndNotUserId(email, id);
    }

    public boolean isUsernameUnique(String username, Integer id) {
        if (username == null) return true;
        return !userRepository.existsByUsernameAndNotUserId(username, id);
    }
}
