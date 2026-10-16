package com.ikram.cool_art_social_media_server.service.impl;

import com.ikram.cool_art_social_media_server.dto.request.ProfileRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.repository.ProfileRepository;
import com.ikram.cool_art_social_media_server.service.ProfileService;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ApiResponse getProfile(String username) {
        Map<String, Object> profile = profileRepository.findByUsername(username);
        if (profile != null) {
            return ApiResponse.builder().exist(true).data(profile).build();
        }
        return ApiResponse.builder().exist(false).data(null).build();
    }

    @Override
    public ApiResponse saveOrUpdateProfile(String username, ProfileRequest request, MultipartFile file) {
        try {
            byte[] avatarBytes = null;
            if (file != null && !file.isEmpty()) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Thumbnails.of(file.getInputStream()).size(800, 800).outputFormat("png").toOutputStream(os);
                avatarBytes = os.toByteArray();
            }

            Map<String, Object> existingProfile = profileRepository.findByUsername(username);

            if (existingProfile == null) {
                profileRepository.insertProfile(username, request.getFullname(), request.getAge(),
                        request.getEmail(), request.getPhone(), request.getLocation(),
                        request.getGender(), request.getArtstyle(), request.getBio(), avatarBytes);
                return ApiResponse.builder().isSuccess(true).message("Profile is created").build();
            } else {
                profileRepository.updateProfile(username, request.getFullname(), request.getAge(),
                        request.getEmail(), request.getPhone(), request.getLocation(),
                        request.getGender(), request.getArtstyle(), request.getBio(), avatarBytes);
                return ApiResponse.builder().isSuccess(true).message("Profile is updated").build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving profile: " + e.getMessage());
        }
    }
}