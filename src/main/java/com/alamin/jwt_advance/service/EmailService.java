package com.alamin.jwt_advance.service;

public interface EmailService {
    void sendPassword(String to, String password,String username);
}
