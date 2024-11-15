package com.w.ever.files.manager.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @PostMapping(path = "login")
    public void login() {
    }
}
