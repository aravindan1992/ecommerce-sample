package com.example.userservice.service;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.exception.InvalidEmailException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    
    private static final String EMAIL_REGEX = 
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        log.info("Fetching user details for email: {}", email);
        
        // Validate email format
        if (!isValidEmail(email)) {
            log.error("Invalid email format provided: {}", email);
            throw new InvalidEmailException(email);
        }
        
        // Perform case-insensitive lookup
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new UserNotFoundException(email);
                });
        
        log.info("Successfully retrieved user details for email: {}", email);
        
        return mapToUserResponse(user);
    }
    
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}