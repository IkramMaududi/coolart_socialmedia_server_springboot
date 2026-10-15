package com.ikram.cool_art_social_media_server.dto.request;

import lombok.Data;

@Data
public class ArtworkRequest {
    private String title;
    private String author;
    private String description;
}
