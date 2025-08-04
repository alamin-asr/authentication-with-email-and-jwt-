package com.alamin.jwt_advance.dtos;

import lombok.Data;

@Data
public class UpDatePasswordDto {
    String oldPassword;
    String newPassword;
}
