package com.w.ever.files.manager.services;

import com.w.ever.files.manager.dto.users.RegisterDTO;
import com.w.ever.files.manager.dto.users.UpdateUserDTO;
import com.w.ever.files.manager.models.GroupUserModel;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.GroupUserRepository;
import com.w.ever.files.manager.repositories.UserRepository;
import com.w.ever.files.manager.utiles.JwtTokenUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final GroupUserRepository groupUserRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, GroupUserRepository groupUserRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.groupUserRepository = groupUserRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Transactional
    public UserModel getProfile(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public UserModel getProfileByUsername(String username) {
        return userRepository.findUserByUsername(username).orElse(null);
    }

    @Transactional
    public Map register(RegisterDTO userData) {
        String name = userData.getName();
        String username = userData.getUsername();
        String email = userData.getEmail();
        String password = passwordEncoder.encode(userData.getPassword());
        UserModel user = userRepository.save(new UserModel(name, username, email, password));
        return new HashMap<Object, Object>() {{
            put("token", jwtTokenUtil.generateToken(user.getId()));
            put("model", user);
        }};
    }

    @Transactional
    public UserModel updateUser(Integer id, UpdateUserDTO userData) throws BadRequestException {
        UserModel user = getProfile(id);
        if (user == null) throw new BadRequestException("User Doesn't Exist");

        if (userData.getName() != null) {
            user.setName(userData.getName());
        }
        if (userData.getUsername() != null) {
            String username = userData.getUsername();
            if (isUsernameUnique(username, id)) {
                user.setUsername(username);
            } else {
                throw new BadRequestException("Username is used");
            }
        }
        if (userData.getEmail() != null) {
            String email = userData.getEmail();
            System.out.println(email);
            if (isEmailUnique(email, id)) {
                user.setEmail(email);
            } else {
                throw new BadRequestException("Email is used");
            }
        }
        if (userData.getPassword() != null) {
            user.setPassword(userData.getPassword());
        }

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Integer id) throws BadRequestException {
        if (!userRepository.exists(id)) {
            throw new BadRequestException("User doesn't exist.");
        }
        /* TODO: Implement Soft Delete */
        userRepository.deleteById(id);
    }

    @Transactional
    public boolean isEmailUnique(String email, Integer id) {
        if (email == null) return true;
        return !userRepository.existsByEmailAndNotUserId(email, id);
    }

    @Transactional
    public boolean isUsernameUnique(String username, Integer id) {
        if (username == null) return true;
        return !userRepository.existsByUsernameAndNotUserId(username, id);
    }

    @Transactional
    public void acceptInvitation(Integer invitationId) throws BadRequestException {
        GroupUserModel invitation = groupUserRepository.findById(invitationId).orElse(null);
        if (invitation == null) throw new BadRequestException("Invitation ID is invalid");
        if (invitation.getInvitationExpiresAt().isAfter(LocalDateTime.now())) {
            invitation.setInvitationExpiresAt(null);
            invitation.setJoinedAt(LocalDateTime.now());
            groupUserRepository.save(invitation);
        } else {
            throw new BadRequestException("Invitation is expired");
        }
    }
}
