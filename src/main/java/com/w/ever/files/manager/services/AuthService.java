package com.w.ever.files.manager.services;

import com.w.ever.files.manager.exceptions.ErrorResponseException;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.UserRepository;
import com.w.ever.files.manager.responses.SuccessResponse;
import com.w.ever.files.manager.utiles.JwtTokenUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private UserService userService;
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

    public UserModel getCurrentUser() throws BadRequestException {
        String username = null;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && ((authentication.getPrincipal()) instanceof UserDetails)) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        if (username == null) {
            throw new BadRequestException("Unauthenticated");
        }

        UserModel userModel = userService.getProfileByUsername(username);
        if (userModel == null) {
            throw new BadRequestException("Unauthenticated");
        }

        return userModel;
    }
}
