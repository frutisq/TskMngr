package com.example.tskmngr.dto;

import lombok.Data;

@Data
public class JwtAuthentificationDto {
    private String token;
    private String refreshToken;

}
