package com.ikram.cool_art_social_media_server.service.impl;

import com.ikram.cool_art_social_media_server.dto.request.AuthRequest;
import com.ikram.cool_art_social_media_server.dto.response.AuthResponse;
import com.ikram.cool_art_social_media_server.repository.UserRepository;
import com.ikram.cool_art_social_media_server.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(AuthRequest request) {
        log.info("Trying the process of registering this username: {}", request.getUsername());

        try {
            String passwordHash = passwordEncoder.encode(request.getPassword());
            userRepository.saveUser(request.getUsername(), passwordHash);

            log.info("User '{}' is managed to be registered into the database", request.getUsername());
            return AuthResponse.builder()
                    .status(true)
                    .username(request.getUsername())
                    .message("Sign Up Successful!")
                    .build();
        } catch (Exception e) {
            log.error("Fail to register user of'{}'. The cause is: {}", request.getUsername(), e.getMessage(), e);
            throw new RuntimeException("Error: Username might already exist.");
        }
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        log.info("Trying to login for username of : {}", request.getUsername());
        Map<String, Object> user = userRepository.findByUsername(request.getUsername());

        if (user != null) {
            String dbPassword = (String) user.get("password");
            if (passwordEncoder.matches(request.getPassword(), dbPassword)) {
                log.info("Login is success for user: {}", request.getUsername());
                return AuthResponse.builder()
                        .status(true)
                        .username(request.getUsername())
                        .message("Sign In Successful")
                        .build();
            } else {
                log.warn("Login failed: Password is wrong for user '{}'", request.getUsername());
                return AuthResponse.builder()
                        .status(false)
                        .message("Wrong username/password combo!")
                        .build();
            }
        }
        log.warn("Login failed: User '{}' is not found in database", request.getUsername());
        return AuthResponse.builder()
                .status(false)
                .message("User doesn't exist")
                .build();
    }
}