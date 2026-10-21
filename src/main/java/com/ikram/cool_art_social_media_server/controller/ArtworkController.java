package com.ikram.cool_art_social_media_server.controller;

import com.ikram.cool_art_social_media_server.dto.request.ArtworkRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.service.ArtworkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/user/artwork")
@RequiredArgsConstructor
@Slf4j
public class ArtworkController {

    private final ArtworkService artworkService;

    @PostMapping
    public ApiResponse uploadArtwork(
            Principal principal,
            @ModelAttribute ArtworkRequest request,
            @RequestParam("image") MultipartFile file
    ) {
        String username = principal.getName();

        log.info("Incoming POST Request : /user/artwork & username: {} & file name: {} & size: {} bytes",
                username, file.getOriginalFilename(), file.getSize());
        return artworkService.uploadArtwork(username, request, file);
    }

    @GetMapping
    public ApiResponse getArtworks(Principal principal) {
        String username = principal.getName();

        log.info("Incoming GET Request : /user/artwork for username: {}", username);
        return artworkService.getArtworks(username);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteArtwork(
            Principal principal,
            @PathVariable("id") Long id
    ) {
        String username = principal.getName();

        log.info("Incoming DELETE Request : /user/artwork/{} for username {}", id, username);
        return artworkService.deleteArtwork(id, username);
    }
}