package com.ikram.cool_art_social_media_server.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ArtworkRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveArtwork(String username, byte[] imageMod, String title, String author, String description) {
        String sql = "INSERT INTO imageart (username, image, title, author, description) VALUES (?, ?, ?, ?, ?)";

        // set default value (manual coalesce) for the sql query if any value is null
        author = (author == null) ? "anonymous" : author;
        description = (description == null) ? "no description" : description;

        jdbcTemplate.update(sql, username, imageMod, title, author, description);
    }

    public List<Map<String, Object>> findArtworksByUsername(String username) {
        String sql = "SELECT * FROM imageart WHERE username = ?";
        // queryForList to return array of objects
        return jdbcTemplate.queryForList(sql, username);
    }

}
