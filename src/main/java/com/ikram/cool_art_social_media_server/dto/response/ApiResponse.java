package com.ikram.cool_art_social_media_server.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private Boolean exist;     // to tell whether data exist or not
    private Object data;       // Type of Object; will be filled with Artwork, list profile, etc
    private Integer total;     // for rowCount
    private String message;    // for success or error message
    private Boolean isSuccess; // result process of uploading or saving result
}
