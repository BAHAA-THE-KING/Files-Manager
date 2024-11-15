package com.w.ever.files.manager.services;

import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.UserRepository;
import com.w.ever.files.manager.responses.ApiErrorResponse;
import com.w.ever.files.manager.responses.ApiResponse;
import com.w.ever.files.manager.utiles.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public String login(String username, String password) {
        UserModel user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        if(!passwordEncoder.matches(password, user.getPassword())){
            System.out.println(password);
            System.out.println(user.getPassword());
            return "Bad credentials";
        }
        return "JWT:  "+jwtTokenUtil.generateToken(user.getId());
    }

    public boolean register(String username, String password) {
        return true;
    }
}
