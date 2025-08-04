package com.alamin.jwt_advance.service;

import com.alamin.jwt_advance.dtos.LoginRequest;
import com.alamin.jwt_advance.dtos.RegisterRequest;
import com.alamin.jwt_advance.entity.User;

import java.security.Principal;
import java.util.List;

public interface AuthService {
   String register(RegisterRequest registerRequest);
   String login(LoginRequest loginRequest);
   void deleteByUserId(Long id);
   String updatePassword(com.alamin.jwt_advance.dtos.UpDatePasswordDto upDatePasswordDto, Principal principal);
   List<User> getAllUser();


}
