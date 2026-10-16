package com.ikram.cool_art_social_media_server.service;

import com.ikram.cool_art_social_media_server.dto.request.ProfileRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ApiResponse getProfile(String username);
    ApiResponse saveOrUpdateProfile(String username, ProfileRequest request, MultipartFile file);
}