package com.ikram.cool_art_social_media_server.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String message;
    private Boolean status;
    private String token;      // for JWT token
    private String username;
}
