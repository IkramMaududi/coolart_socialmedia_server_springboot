package com.ikram.cool_art_social_media_server.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
