package com.bvg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthToken {
    private String access_token;
    private String refresh_token;
    private String refresh_expires_in;
    private String expires_in;
}
