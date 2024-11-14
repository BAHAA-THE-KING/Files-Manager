package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.responses.ApiResponse;
import com.w.ever.files.manager.responses.SuccessApiResponse;
import com.w.ever.files.manager.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ApiResponse profile(@PathVariable Integer id) {
        UserModel userModel =userService.getProfile();
        return new SuccessApiResponse(fileModel);
    }

    @PutMapping("/user")
    public ApiResponse uploadFile(@RequestBody FileModel file, @PathVariable Integer id) {
        FileModel fileModel = fileService.updateFile(file, id);
        return new SuccessApiResponse(fileModel);
    }

    @DeleteMapping("/user")
    public void deleteFile(@PathVariable String id) {
        fileService.delete(id);
    }
}
