package com.yozora.anime.entity;

import lombok.Data;

import java.util.Date;

@Data
public class JwtResponseEntity {

    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private String username;
    private String role;
    private Date expiration;

    public JwtResponseEntity(String token, String refreshToken, String username, String role, Date expiration) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.username = username;
        this.role = role;
        this.expiration = expiration;
    }

}
