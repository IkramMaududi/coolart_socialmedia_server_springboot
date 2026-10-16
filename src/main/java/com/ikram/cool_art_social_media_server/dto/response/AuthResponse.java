package com.ikram.cool_art_social_media_server.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private Boolean status;
    private String username;
    private String message;
}
