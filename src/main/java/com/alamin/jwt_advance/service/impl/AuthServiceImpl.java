package com.alamin.jwt_advance.service.impl;


import com.alamin.jwt_advance.dtos.LoginRequest;
import com.alamin.jwt_advance.dtos.RegisterRequest;
import com.alamin.jwt_advance.dtos.UpDatePasswordDto;
import com.alamin.jwt_advance.entity.User;
import com.alamin.jwt_advance.repository.RoleRepository;
import com.alamin.jwt_advance.repository.UserRepository;
import com.alamin.jwt_advance.service.AuthService;
import com.alamin.jwt_advance.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailServiceImpl emailServiceImpl;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    public String  register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()) || userRepository.existsByUsername(request.getUsername())) {
        //if (userRepository.existsByUsername(request.getUsername())) {
            return "User already exists";
        }
        String rawPassword =UUID.randomUUID().toString().substring(0, 8);
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(rawPassword));
        //user.setPassword(rawPassword);

       user.setRole(roleRepository.findByName("ADMIN").orElseThrow());
        userRepository.save(user);

        emailServiceImpl.sendPassword(user.getEmail(), rawPassword,user.getUsername());
        return "Password sent to email";
    }

    public String login(LoginRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }
        String username = request.getUsername().trim();
        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found with username: "+username));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, request.getPassword()));



        String token = jwtUtil.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(), user.getPassword(),
                        user.getRole().getPermissions().stream()
                                .map(p ->
                                        new SimpleGrantedAuthority(p.getPermission().getNameEn()))
                                .toList()));
        return token;
    }
    public String updatePassword(UpDatePasswordDto upDatePasswordDto, Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new RuntimeException("You are not authorized to update password");
        }
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found with username: "+principal.getName()));
        if (!bCryptPasswordEncoder.matches(upDatePasswordDto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        user.setPassword(bCryptPasswordEncoder.encode(upDatePasswordDto.getNewPassword()));
        userRepository.save(user);
        emailServiceImpl.sendPassword(user.getEmail(), upDatePasswordDto.getNewPassword(),user.getUsername());
        return "Password updated";


    }

    public void deleteByUserId(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
