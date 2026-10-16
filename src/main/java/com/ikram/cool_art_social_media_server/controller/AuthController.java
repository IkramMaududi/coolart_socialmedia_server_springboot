package com.ikram.cool_art_social_media_server.controller;

import com.ikram.cool_art_social_media_server.dto.request.AuthRequest;
import com.ikram.cool_art_social_media_server.dto.response.AuthResponse;
import com.ikram.cool_art_social_media_server.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return userService.login(request);
    }
}
