package com.example.userservice.service;

import com.example.userservice.exception.InvalidEmailException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class UserService {
    
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Retrieves user details by email ID with case-insensitive lookup
     * 
     * @param email the email ID to search for
     * @return User object containing user details
     * @throws InvalidEmailException if email format is invalid
     * @throws UserNotFoundException if user does not exist
     */
    public User getUserByEmail(String email) {
        log.info("Attempting to retrieve user with email: {}", email);
        
        // Validate email format
        if (!isValidEmail(email)) {
            log.error("Invalid email format: {}", email);
            throw new InvalidEmailException("Invalid email format: " + email);
        }
        
        // Perform case-insensitive lookup
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new UserNotFoundException("User not found with email: " + email);
                });
        
        log.info("Successfully retrieved user with email: {}", email);
        return user;
    }
    
    /**
     * Validates email format using regex pattern
     * 
     * @param email the email to validate
     * @return true if email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}