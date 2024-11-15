package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.dto.RegisterDTO;
import com.w.ever.files.manager.dto.UpdateUserDTO;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.responses.ApiResponse;
import com.w.ever.files.manager.responses.ErrorApiResponse;
import com.w.ever.files.manager.responses.SuccessApiResponse;
import com.w.ever.files.manager.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public UserModel register(@Valid @RequestBody RegisterDTO userData) {
        return this.userService.register(userData);
    }

    @GetMapping("{id}")
    public ApiResponse getProfile(@PathVariable Integer id) {
        UserModel userModel = userService.getProfile(id);
        if (userModel == null) {
            return new ErrorApiResponse(null, List.of("User not found"));
        }
        return new SuccessApiResponse(userModel);
    }

    @PutMapping("{id}")
    public ApiResponse updateUser(@Valid @RequestBody UpdateUserDTO userData, @PathVariable @NotNull(message = "User ID cannot be null") Integer id) throws BadRequestException {
        UserModel userModel = userService.updateUser(id, userData);
        return new SuccessApiResponse(userModel);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }
}
