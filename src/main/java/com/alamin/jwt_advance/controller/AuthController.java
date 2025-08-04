package com.alamin.jwt_advance.controller;

import com.alamin.jwt_advance.dtos.LoginRequest;
import com.alamin.jwt_advance.dtos.RegisterRequest;
import com.alamin.jwt_advance.dtos.UpDatePasswordDto;
import com.alamin.jwt_advance.entity.User;
import com.alamin.jwt_advance.service.impl.AuthServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private AuthServiceImpl authServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
       String msg= authServiceImpl.register(registerRequest);
        return ResponseEntity.ok(msg);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authServiceImpl.login(loginRequest);
        return ResponseEntity.ok(token);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        authServiceImpl.deleteByUserId(id);
        return ResponseEntity.ok("User deleted");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){

        return ResponseEntity.ok(authServiceImpl.getAllUser());
    }
    @PutMapping("/update_password")
    public ResponseEntity<String> updatePassword(@RequestBody UpDatePasswordDto upDatePasswordDto, Principal principal){
        if(principal==null || principal.getName()==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    "You are not authorized to update password");
        }
     String msg= authServiceImpl.updatePassword(upDatePasswordDto,principal);
      return ResponseEntity.ok(msg);
    }


}