package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Slf4j
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * GET endpoint to retrieve user by email
     * 
     * @param email the email ID to search for
     * @return ResponseEntity containing User object and HTTP status
     */
    @GetMapping
    public ResponseEntity<User> getUserByEmail(
            @RequestParam 
            @NotBlank(message = "Email parameter is required") 
            @Email(message = "Email should be valid") 
            String email) {
        
        log.info("Received request to get user by email: {}", email);
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    
    /**
     * GET endpoint to retrieve user by email (alternative path variable approach)
     * 
     * @param email the email ID to search for
     * @return ResponseEntity containing User object and HTTP status
     */
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmailPath(
            @PathVariable 
            @NotBlank(message = "Email is required") 
            @Email(message = "Email should be valid") 
            String email) {
        
        log.info("Received request to get user by email (path): {}", email);
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}