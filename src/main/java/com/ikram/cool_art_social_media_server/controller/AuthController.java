package com.ikram.cool_art_social_media_server.controller;

import com.ikram.cool_art_social_media_server.dto.request.AuthRequest;
import com.ikram.cool_art_social_media_server.dto.response.AuthResponse;
import com.ikram.cool_art_social_media_server.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        log.info("Accepting REGISTER request for username: {}", request.getUsername());
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        log.info("Accepting LOGIN request for username: {}", request.getUsername());
        return userService.login(request);
    }
}
