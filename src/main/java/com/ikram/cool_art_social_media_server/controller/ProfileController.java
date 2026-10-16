package com.ikram.cool_art_social_media_server.controller;

import com.ikram.cool_art_social_media_server.dto.request.ProfileRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.service.ProfileService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ApiResponse getProfile(@RequestHeader("username") String username) {
        return profileService.getProfile(username);
    }

    @PostMapping
    public ApiResponse updateProfile(
            @RequestHeader("username") String username,
            @ModelAttribute ProfileRequest request,
            @RequestParam(value = "avatar", required = false) MultipartFile file
    ) {
        return profileService.saveOrUpdateProfile(username, request, file);
    }
}