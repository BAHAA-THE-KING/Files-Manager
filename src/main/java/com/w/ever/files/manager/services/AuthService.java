package com.w.ever.files.manager.services;

import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.utiles.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        UserModel user = (UserModel) authentication.getPrincipal();
        return jwtTokenUtil.generateToken(user.getId());
    }

    public boolean register(String username, String password) {
        return true;
    }
}
