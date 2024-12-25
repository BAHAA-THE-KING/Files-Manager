package com.w.ever.files.manager.services;

import com.w.ever.files.manager.exceptions.ErrorResponseException;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.UserRepository;
import com.w.ever.files.manager.responses.SuccessResponse;
import com.w.ever.files.manager.utiles.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public ResponseEntity login(String email, String password) {
        UserModel user = userRepository.findUserByEmail(email).orElse(null);
        if (user == null) {
            throw new ErrorResponseException("Wrong credentials");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ErrorResponseException("Wrong credentials");
        }
        return new SuccessResponse(new HashMap<String, Object>() {{
            put("token", jwtTokenUtil.generateToken(user.getId()));
            put("user", user);
        }});
    }

}
