package com.ikram.cool_art_social_media_server.service;

import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;

import java.util.Map;

public interface GameService {
    ApiResponse getGames(String username);
    ApiResponse saveGameResult(String username, String gameName, Map<String, Object> payload);
}