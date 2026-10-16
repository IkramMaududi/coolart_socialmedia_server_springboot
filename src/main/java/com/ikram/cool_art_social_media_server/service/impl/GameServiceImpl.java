package com.ikram.cool_art_social_media_server.service.impl;

import com.ikram.cool_art_social_media_server.dto.request.GameRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.repository.GameRepository;
import com.ikram.cool_art_social_media_server.service.GameService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public ApiResponse getGames(String username) {
        List<Map<String, Object>> games = gameRepository.findGamesByUsername(username);
        if (!games.isEmpty()) {
            return ApiResponse.builder().exist(true).data(games).build();
        }
        return ApiResponse.builder().exist(false).data(null).build();
    }

    @Override
    public ApiResponse saveGameResult(String username, String gameName, GameRequest request) {
        try {
            // request.getResult() is raw JSON from frontend
            gameRepository.insertGameResult(username, gameName, request.getResult());
            return ApiResponse.builder().isSuccess(true).message("Game result successfully inserted").build();
        } catch (Exception e) {
            throw new RuntimeException("Error saving game result: " + e.getMessage());
        }
    }
}