package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.dto.LoginDTO;
import com.w.ever.files.manager.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @PostMapping(path = "login")
    public String login(@Valid @RequestBody LoginDTO loginData) {
        return this.authService.login(loginData.getUsername(), loginData.getPassword());
    }

    @PostMapping(path = "register")
    public boolean register(@RequestBody String userName) {
        return this.authService.register(userName, userName);
    }
}
