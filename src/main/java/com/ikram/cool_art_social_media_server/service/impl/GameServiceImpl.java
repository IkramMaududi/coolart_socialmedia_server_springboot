package com.ikram.cool_art_social_media_server.service.impl;

import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.repository.GameRepository;
import com.ikram.cool_art_social_media_server.service.GameService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public ApiResponse getGames(String username) {
        log.info("Looking for game history in database for this user: {}", username);
        List<Map<String, Object>> games = gameRepository.findGamesByUsername(username);

        if (!games.isEmpty()) {
            log.info("Managed to find {} game history for this user: {}", games.size(), username);
            return ApiResponse.builder().exist(true).data(games).build();
        }

        log.info("No game history for this user: {}", username);
        return ApiResponse.builder().exist(false).data(null).build();
    }

    @Override
    public ApiResponse saveGameResult(String username, String gameName, Map<String, Object> payload) {
        log.info("Raw data from frontend: [{}]", payload);

        try {
            // create object mapper manually
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            String validJsonResult = mapper.writeValueAsString(payload);

            log.info("Data JSON to be inserted to DB : [{}]", validJsonResult);

            gameRepository.insertGameResult(username, gameName, validJsonResult);

            log.info("Managed to save game result {} for user: {}", gameName, username);
            return ApiResponse.builder().isSuccess(true).message("Game result successfully inserted").build();
        } catch (Exception e) {

            log.error("Failed to save game result {} for this user {}. The cause is : {}", gameName, username, e.getMessage(), e);
            throw new RuntimeException("Error saving game result: " + e.getMessage());
        }
    }
}