package com.alamin.jwt_advance.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String username;
}