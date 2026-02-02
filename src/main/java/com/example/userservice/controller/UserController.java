package com.example.userservice.controller;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<UserResponse> getUserByEmail(
            @RequestParam(name = "email") String email) {
        
        log.info("Received request to fetch user by email: {}", email);
        
        UserResponse userResponse = userService.getUserByEmail(email);
        
        log.info("Successfully processed request for email: {}", email);
        
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("User Service is running");
    }
}