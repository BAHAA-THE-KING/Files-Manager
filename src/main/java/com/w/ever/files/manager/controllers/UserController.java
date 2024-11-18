package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.dto.users.RegisterDTO;
import com.w.ever.files.manager.dto.users.UpdateUserDTO;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.responses.ErrorResponse;
import com.w.ever.files.manager.responses.SuccessResponse;
import com.w.ever.files.manager.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody RegisterDTO userData) {
        UserModel userModel = this.userService.register(userData);
        return new SuccessResponse(userModel);
    }

    @GetMapping("{id}")
    public ResponseEntity getProfile(@PathVariable @NotNull(message = "User ID cannot be null") Integer id) {
        UserModel userModel = userService.getProfile(id);
        if (userModel == null) {
            return new ErrorResponse(404, "User not found");
        }
        return new SuccessResponse(userModel);
    }

    @PutMapping("{id}")
    public ResponseEntity updateUser(@Valid @RequestBody UpdateUserDTO userData, @PathVariable @NotNull(message = "User ID cannot be null") Integer id) throws BadRequestException {
        UserModel userModel = userService.updateUser(id, userData);
        return new SuccessResponse(userModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable @NotNull(message = "User ID cannot be null") Integer id) throws BadRequestException {
        userService.deleteUser(id);
        return new SuccessResponse();
    }

    @PostMapping("accept-invitation/{invitationId}")
    public ResponseEntity accept(@PathVariable @NotNull(message = "Invitation ID cannot be nul") Integer invitationId) throws BadRequestException {
        userService.acceptInvitation(invitationId);
        return new SuccessResponse();
    }
}
