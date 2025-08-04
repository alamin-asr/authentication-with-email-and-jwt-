package com.alamin.jwt_advance.service.impl;

import com.alamin.jwt_advance.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    public void sendPassword(String to, String password,String username){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Account Password");
        message.setText("Your username is: "+username+"\n"+"Your password is: " + password);
        //message.setText("Your password is: " + password);

        mailSender.send(message);
    }
}
