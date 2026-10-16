package com.ikram.cool_art_social_media_server.service.impl;

import com.ikram.cool_art_social_media_server.dto.request.ArtworkRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.repository.ArtworkRepository;
import com.ikram.cool_art_social_media_server.service.ArtworkService;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArtworkServiceImpl implements ArtworkService {

    private final ArtworkRepository artworkRepository;

    @Override
    public ApiResponse uploadArtwork(String username, ArtworkRequest request, MultipartFile file) {
        try {
            byte[] imageMod = null;
            if (file != null && !file.isEmpty()) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Thumbnails.of(file.getInputStream()).size(800, 800).outputFormat("png").toOutputStream(os);
                imageMod = os.toByteArray();
            }

            artworkRepository.saveArtwork(username, imageMod, request.getTitle(), request.getAuthor(), request.getDescription());

            return ApiResponse.builder().isSuccess(true).message("Image upload success").build();
        } catch (Exception e) {
            throw new RuntimeException("Error processing image: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse getArtworks(String username) {
        List<Map<String, Object>> artworks = artworkRepository.findArtworksByUsername(username);
        if (!artworks.isEmpty()) {
            return ApiResponse.builder().exist(true).data(artworks).total(artworks.size()).build();
        }
        return ApiResponse.builder().exist(false).data(null).total(null).build();
    }
}