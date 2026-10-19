package com.ikram.cool_art_social_media_server.service.impl;

import com.ikram.cool_art_social_media_server.dto.request.ProfileRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.repository.ProfileRepository;
import com.ikram.cool_art_social_media_server.service.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ApiResponse getProfile(String username) {
        log.info("Searching for profile data of this user {} in database", username);
        Map<String, Object> profile = profileRepository.findByUsername(username);

        if (profile != null) {
            log.info("A profile is found for this user: {}", username);
            return ApiResponse.builder().exist(true).data(profile).build();
        }

        log.info("No profile found for this user {}", username);
        return ApiResponse.builder().exist(false).data(null).build();
    }

    @Override
    public ApiResponse saveOrUpdateProfile(String username, ProfileRequest request, MultipartFile file) {
        log.info("Starting saving/updating process for profile of this user: {}", username);

        try {
            byte[] avatarBytes = null;
            if (file != null && !file.isEmpty()) {
                log.info("Compressing avatar using Thumbnailator for this file: {}", file.getOriginalFilename());

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Thumbnails.of(file.getInputStream()).size(800, 800).outputFormat("png").toOutputStream(os);
                avatarBytes = os.toByteArray();

                log.info("Avatar is compressed to this size: {} bytes", avatarBytes.length);
            } else {
                log.info("No new avatar is attached by this user: {}", username);
            }

            log.info("Checking existing profile for this user: {}", username);
            Map<String, Object> existingProfile = profileRepository.findByUsername(username);

            if (existingProfile == null) {
                log.info("Profile is not exist yet. Doing INSERT new profile for this user: {}", username);
                profileRepository.insertProfile(username, request.getFullname(), request.getAge(),
                        request.getEmail(), request.getPhone(), request.getLocation(),
                        request.getGender(), request.getArtstyle(), request.getBio(), avatarBytes);

                log.info("New profile is created successfully for this user: {}", username);
                return ApiResponse.builder().isSuccess(true).message("Profile is created").build();
            } else {
                log.info("Profile already exists. Doing UPDATE profile for this user: {}", username);
                profileRepository.updateProfile(username, request.getFullname(), request.getAge(),
                        request.getEmail(), request.getPhone(), request.getLocation(),
                        request.getGender(), request.getArtstyle(), request.getBio(), avatarBytes);

                log.info("Profile is updated successfully for this user: {}", username);
                return ApiResponse.builder().isSuccess(true).message("Profile is updated").build();
            }
        } catch (Exception e) {

            log.error("Failed to save/update profile for this user {}. The cause is: {}", username, e.getMessage(), e);
            throw new RuntimeException("Error saving profile: " + e.getMessage());
        }
    }
}