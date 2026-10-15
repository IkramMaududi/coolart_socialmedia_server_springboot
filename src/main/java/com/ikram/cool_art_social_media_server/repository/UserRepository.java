package com.ikram.cool_art_social_media_server.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveUser(String username, String passwordHash) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        // jdbcTemplate.update used for DML (Insert, Update, Delete)
        jdbcTemplate.update(sql, username, passwordHash);
    }

    public Map<String, Object> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            // queryForMap will return Key-Value pairs
            return jdbcTemplate.queryForMap(sql, username);
        } catch (EmptyResultDataAccessException e) {
            // if rowCount = 0, set to return null
            return null;
        }
    }



}
