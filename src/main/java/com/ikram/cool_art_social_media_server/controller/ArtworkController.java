package com.ikram.cool_art_social_media_server.controller;

import com.ikram.cool_art_social_media_server.dto.request.ArtworkRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.service.ArtworkService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/artworks")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;

    @PostMapping
    public ApiResponse uploadArtwork(
            @RequestHeader("username") String username,
            @ModelAttribute ArtworkRequest request,
            @RequestParam("image") MultipartFile file
    ) {
        return artworkService.uploadArtwork(username, request, file);
    }

    @GetMapping
    public ApiResponse getArtworks(@RequestHeader("username") String username) {
        return artworkService.getArtworks(username);
    }
}