package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.dto.RegisterDTO;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.responses.ApiResponse;
import com.w.ever.files.manager.responses.ErrorApiResponse;
import com.w.ever.files.manager.responses.SuccessApiResponse;
import com.w.ever.files.manager.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "register")
    public UserModel register(@Valid @RequestBody RegisterDTO userData) {
        return this.userService.register(userData);
    }

    @GetMapping("/user/{id}")
    public ApiResponse getProfile(@PathVariable Integer id) {
        UserModel userModel = userService.getProfile(id);
        if (userModel == null) {
            return new ErrorApiResponse(null, List.of("User not found"));
        }
        return new SuccessApiResponse(userModel);
    }

    @PutMapping("/user/{id}")
    public ApiResponse updateUser(@RequestBody UserModel user, @PathVariable Integer id) {
//        FileModel userModel = userService.updateFile(file, id);
        return new SuccessApiResponse(new UserModel());
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }
}
