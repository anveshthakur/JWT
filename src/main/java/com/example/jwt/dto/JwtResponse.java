package com.example.jwt.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class JwtResponse {
    private boolean success;
    private String message;
    private String jwt;
}
