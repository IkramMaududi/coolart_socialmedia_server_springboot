package com.ikram.cool_art_social_media_server.service;

import com.ikram.cool_art_social_media_server.dto.request.ArtworkRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;

import org.springframework.web.multipart.MultipartFile;

public interface ArtworkService {
    ApiResponse uploadArtwork(String username, ArtworkRequest request, MultipartFile file);
    ApiResponse getArtworks(String username);
}