package com.ikram.cool_art_social_media_server.controller;

import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.service.GameService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/game")
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ApiResponse getGames(@RequestHeader("username") String username) {
        log.info("Fetching data for all games related with username: {}", username);
        return gameService.getGames(username);
    }

    @PostMapping
    public ApiResponse saveGame(
            @RequestHeader("username") String username,
            @RequestHeader("game") String gameName,
            @RequestBody Map<String, Object> payload
    ) {
        log.info("Saving result for of the game for username: {} & game: {}", username, gameName);
        return gameService.saveGameResult(username, gameName, payload);
    }
}