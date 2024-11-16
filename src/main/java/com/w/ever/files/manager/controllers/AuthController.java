package com.w.ever.files.manager.controllers;

import com.w.ever.files.manager.dto.auth.LoginDTO;
import com.w.ever.files.manager.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping(path = "login")
    public ResponseEntity login(@Valid @RequestBody LoginDTO loginData) {
        return this.authService.login(loginData.getUsername(), loginData.getPassword());
    }

    @PostMapping(path = "register")
    public boolean register(@RequestBody String userName) {
        return this.authService.register(userName, userName);
    }
}
