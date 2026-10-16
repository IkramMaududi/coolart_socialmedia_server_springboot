package com.ikram.cool_art_social_media_server.controller;

import com.ikram.cool_art_social_media_server.dto.request.GameRequest;
import com.ikram.cool_art_social_media_server.dto.response.ApiResponse;
import com.ikram.cool_art_social_media_server.service.GameService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ApiResponse getGames(@RequestHeader("username") String username) {
        return gameService.getGames(username);
    }

    @PostMapping
    public ApiResponse saveGame(
            @RequestHeader("username") String username,
            @RequestHeader("game") String gameName,
            @RequestBody GameRequest request
    ) {
        return gameService.saveGameResult(username, gameName, request);
    }
}