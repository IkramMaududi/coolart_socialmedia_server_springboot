package com.ikram.cool_art_social_media_server.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProfileRepository {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> findByUsername(String username) {
        String sql = "SELECT * FROM profile WHERE username = ?";
        try {
            return jdbcTemplate.queryForMap(sql, username);
        } catch (EmptyResultDataAccessException e) {
            return null; //
        }
    }

    public void insertProfile(String username, String fullname, Integer age, String email, String phone,
                              String location, String gender, String artstyle, String bio, byte[] avatar) {
        String sql = "INSERT INTO profile (username, fullname, age, email, phone, location, gender, artstyle, bio, avatar) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, username, fullname, age, email, phone, location, gender, artstyle, bio, avatar);
    }

    public void updateProfile(String username, String fullname, Integer age, String email, String phone,
                              String location, String gender, String artstyle, String bio, byte[] avatar) {
        // COALESCE digunakan agar jika avatar yang dikirim NULL (tidak upload foto baru),
        String sql = "UPDATE profile SET fullname = ?, age = ?, email = ?, phone = ?, location = ?, " +
                "gender = ?, artstyle = ?, bio = ?, avatar = COALESCE(?, avatar) WHERE username = ?";
        jdbcTemplate.update(sql, fullname, age, email, phone, location, gender, artstyle, bio, avatar, username);
    }
}