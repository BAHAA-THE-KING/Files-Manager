package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "login")
    public boolean login(@RequestBody String userName) {
        return this.authService.login(userName, userName);
    }

    @PostMapping(path = "register")
    public boolean register(@RequestBody String userName) {
        return this.authService.register(userName, userName);
    }
}
