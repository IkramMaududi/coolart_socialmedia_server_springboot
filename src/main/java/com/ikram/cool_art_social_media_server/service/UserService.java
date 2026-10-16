package com.ikram.cool_art_social_media_server.service;

import com.ikram.cool_art_social_media_server.dto.request.AuthRequest;
import com.ikram.cool_art_social_media_server.dto.response.AuthResponse;

public interface UserService {
    AuthResponse register(AuthRequest request);
    AuthResponse login(AuthRequest request);
}