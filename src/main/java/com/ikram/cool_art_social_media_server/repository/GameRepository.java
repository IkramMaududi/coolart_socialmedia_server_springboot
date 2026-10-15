package com.ikram.cool_art_social_media_server.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GameRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findGamesByUsername(String username) {
        String sql = "SELECT * FROM play WHERE username = ?";
        return jdbcTemplate.queryForList(sql, username);
    }

    public void insertGameResult(String username, String game, String jsonResult) {
        // ?::json reads real JSON
        String sql = "INSERT INTO play (username, game, result) VALUES (?, ?, ?::json)";
        jdbcTemplate.update(sql, username, game, jsonResult);
    }
}