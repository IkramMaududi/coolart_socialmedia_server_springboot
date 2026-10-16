package com.ikram.cool_art_social_media_server.service.impl;

import com.ikram.cool_art_social_media_server.dto.request.AuthRequest;
import com.ikram.cool_art_social_media_server.dto.response.AuthResponse;
import com.ikram.cool_art_social_media_server.repository.UserRepository;
import com.ikram.cool_art_social_media_server.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(AuthRequest request) {
        try {
            String passwordHash = passwordEncoder.encode(request.getPassword());
            userRepository.saveUser(request.getUsername(), passwordHash);

            return AuthResponse.builder()
                    .status(true)
                    .message("Sign Up Successful!")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error: Username might already exist.");
        }
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        Map<String, Object> user = userRepository.findByUsername(request.getUsername());

        if (user != null) {
            String dbPassword = (String) user.get("password");
            if (passwordEncoder.matches(request.getPassword(), dbPassword)) {
                return AuthResponse.builder()
                        .status(true)
                        .username(request.getUsername())
                        .message("Sign In Successful")
                        .build();
            } else {
                return AuthResponse.builder()
                        .status(false)
                        .message("Wrong username/password combo!")
                        .build();
            }
        }
        return AuthResponse.builder()
                .status(false)
                .message("User doesn't exist")
                .build();
    }
}