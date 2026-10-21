package com.ikram.cool_art_social_media_server.controller;

import com.ikram.cool_art_social_media_server.dto.request.ProfileRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.service.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ApiResponse getProfile(Principal principal) {
        String username = principal.getName();

        log.info("Incoming Request : GET /user/profiles for username: {}", username);
        return profileService.getProfile(username);
    }

    @PostMapping
    public ApiResponse updateProfile(
            Principal principal,
            @ModelAttribute ProfileRequest request,
            @RequestParam(value = "avatar", required = false) MultipartFile file
    ) {
        String username = principal.getName();

        log.info("Incoming Request : POST /user/profiles for username {}", username);
        return profileService.saveOrUpdateProfile(username, request, file);
    }
}