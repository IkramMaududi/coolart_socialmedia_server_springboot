package com.ikram.cool_art_social_media_server.dto.request;

import lombok.Data;

@Data
public class ProfileRequest {
    private String fullname;
    private Integer age;
    private String email;
    private String phone;
    private String location;
    private String gender;
    private String artstyle;
    private String bio;
}
