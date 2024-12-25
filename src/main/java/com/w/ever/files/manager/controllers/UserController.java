package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.dto.users.RegisterDTO;
import com.w.ever.files.manager.dto.users.UpdateUserDTO;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.responses.ErrorResponse;
import com.w.ever.files.manager.responses.SuccessResponse;
import com.w.ever.files.manager.services.NotificationsService;
import com.w.ever.files.manager.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final NotificationsService notificationsService;

    public UserController(UserService userService, NotificationsService notificationsService) {
        this.userService = userService;
        this.notificationsService = notificationsService;
    }

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody RegisterDTO userData) {
        return new SuccessResponse(this.userService.register(userData));
    }

    @GetMapping("profile")
    public ResponseEntity getMyProfile() {
        String username = null;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && ((authentication.getPrincipal()) instanceof UserDetails)) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        if (username == null) {
            return new ErrorResponse(404, "User not found");
        }

        UserModel userModel = userService.getProfileByUsername(username);
        if (userModel == null) {
            return new ErrorResponse(404, "User not found");
        }

        return new SuccessResponse(userModel);
    }

    @GetMapping("{id}")
    public ResponseEntity getUserProfile(@PathVariable @NotNull(message = "User ID cannot be null") Integer id) {
        UserModel userModel = userService.getProfile(id);
        if (userModel == null) {
            return new ErrorResponse(404, "User not found");
        }
        return new SuccessResponse(userModel);
    }

    @PutMapping("{id}")
    public ResponseEntity updateUser(@Valid @RequestBody UpdateUserDTO userData, @PathVariable @NotNull(message = "User ID cannot be null") Integer id) throws BadRequestException {
        UserModel userModel = userService.updateUser(id, userData);
        /* TODO: Send Notifications */
        return new SuccessResponse(userModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable @NotNull(message = "User ID cannot be null") Integer id) throws BadRequestException {
        userService.deleteUser(id);
        /* TODO: Send Notifications */
        return new SuccessResponse();
    }

    @PostMapping("accept-invitation/{invitationId}")
    public ResponseEntity accept(@PathVariable @NotNull(message = "Invitation ID cannot be nul") Integer invitationId) throws BadRequestException {
        userService.acceptInvitation(invitationId);
        /* TODO: Send Notifications */
        return new SuccessResponse();
    }
}
