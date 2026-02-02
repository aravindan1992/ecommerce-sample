package com.example.userservice.controller;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    
    private final UserService userService;
    
    /**
     * Retrieve user details by email ID
     * @param email the email to search for
     * @return ResponseEntity containing user details
     */
    @GetMapping
    public ResponseEntity<UserResponse> getUserByEmail(
            @RequestParam 
            @NotBlank(message = "Email parameter is required") 
            @Email(message = "Email should be valid") 
            String email) {
        
        log.info("Received request to get user by email: {}", email);
        
        User user = userService.getUserByEmail(email);
        UserResponse response = mapToUserResponse(user);
        
        log.info("Successfully returned user details for email: {}", email);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Alternative endpoint: Retrieve user details by email ID using path variable
     * @param email the email to search for
     * @return ResponseEntity containing user details
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmailPath(
            @PathVariable 
            @NotBlank(message = "Email is required") 
            @Email(message = "Email should be valid") 
            String email) {
        
        log.info("Received request to get user by email (path): {}", email);
        
        User user = userService.getUserByEmail(email);
        UserResponse response = mapToUserResponse(user);
        
        log.info("Successfully returned user details for email: {}", email);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Health check endpoint
     * @return ResponseEntity with OK status
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("User Service is running");
    }
    
    /**
     * Map User entity to UserResponse DTO
     * @param user the user entity
     * @return UserResponse DTO
     */
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .city(user.getCity())
                .country(user.getCountry())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}