package com.ikram.cool_art_social_media_server.service.impl;

import com.ikram.cool_art_social_media_server.dto.request.ArtworkRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.repository.ArtworkRepository;
import com.ikram.cool_art_social_media_server.service.ArtworkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtworkServiceImpl implements ArtworkService {

    private final ArtworkRepository artworkRepository;

    @Override
    public ApiResponse uploadArtwork(String username, ArtworkRequest request, MultipartFile file) {
        log.info("Starting the process of uploading artwork '{}' for user: {}", request.getTitle(), username);
        try {
            byte[] imageMod = null;
            if (file != null && !file.isEmpty()) {
                log.info("Compressing artwork using Thumbnailator for file: {}", file.getOriginalFilename());

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Thumbnails.of(file.getInputStream()).size(800, 800).outputFormat("png").toOutputStream(os);
                imageMod = os.toByteArray();

                log.info("Artwork is compressed to : {} bytes", imageMod.length);
            }

            log.info("Saving artwork to database");
            artworkRepository.saveArtwork(username, imageMod, request.getTitle(), request.getAuthor(), request.getDescription());

            log.info("Artwork '{}' is saved for this user : {}", request.getTitle(), username);
            return ApiResponse.builder().isSuccess(true).message("Image upload success").build();
        } catch (Exception e) {
            log.error("Fail to process/save artwork '{}' for user {}. The cause is: {}", request.getTitle(), username, e.getMessage(), e);
            throw new RuntimeException("Error processing image: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse getArtworks(String username) {
        log.info("Fetching artwork list from database for user: {}", username);
        List<Map<String, Object>> artworks = artworkRepository.findArtworksByUsername(username);

        if (!artworks.isEmpty()) {
            log.info("Managed to find {} artwork for user: {}", artworks.size(), username);
            return ApiResponse.builder().exist(true).data(artworks).total(artworks.size()).build();
        }

        log.info("No artwork found for user: {}", username);
        return ApiResponse.builder().exist(false).data(null).total(null).build();
    }
}