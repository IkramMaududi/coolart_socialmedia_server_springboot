package com.ikram.cool_art_social_media_server.controller;

import com.ikram.cool_art_social_media_server.dto.request.ArtworkRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.service.ArtworkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user/artwork")
@RequiredArgsConstructor
@Slf4j
public class ArtworkController {

    private final ArtworkService artworkService;

    @PostMapping
    public ApiResponse uploadArtwork(
            @RequestHeader("username") String username,
            @ModelAttribute ArtworkRequest request,
            @RequestParam("image") MultipartFile file
    ) {
        log.info("Incoming POST Request : /user/artwork & username: {} & file name: {} & size: {} bytes",
                username, file.getOriginalFilename(), file.getSize());
        return artworkService.uploadArtwork(username, request, file);
    }

    @GetMapping
    public ApiResponse getArtworks(@RequestHeader("username") String username) {
        log.info("Incoming GET Request : /user/artwork for username: {}", username);
        return artworkService.getArtworks(username);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteArtwork(
            @RequestHeader("username") String username,
            @PathVariable("id") Long id
    ) {
        log.info("Incoming DELETE Request : /user/artwork/{} for username {}", id, username);
        return artworkService.deleteArtwork(id, username);
    }
}