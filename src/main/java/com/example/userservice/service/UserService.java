package com.example.userservice.service;

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
    
    // RFC 5322 compliant email regex pattern
    private static final String EMAIL_REGEX = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    /**
     * Retrieve user details by email ID
     * @param email the email to search for
     * @return User object if found
     * @throws InvalidEmailException if email format is invalid
     * @throws UserNotFoundException if user is not found
     */
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        log.info("Attempting to retrieve user with email: {}", email);
        
        // Validate email format
        if (email == null || email.trim().isEmpty()) {
            log.error("Email is null or empty");
            throw new InvalidEmailException("Email cannot be null or empty");
        }
        
        if (!isValidEmail(email)) {
            log.error("Invalid email format: {}", email);
            throw new InvalidEmailException("Invalid email format: " + email);
        }
        
        // Perform case-insensitive lookup
        User user = userRepository.findByEmailIgnoreCase(email.trim())
            .orElseThrow(() -> {
                log.error("User not found with email: {}", email);
                return new UserNotFoundException("User not found with email: " + email);
            });
        
        log.info("Successfully retrieved user with email: {}", email);
        return user;
    }
    
    /**
     * Validate email format using regex pattern
     * @param email the email to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Check if user exists by email
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean userExists(String email) {
        log.debug("Checking if user exists with email: {}", email);
        return userRepository.existsByEmailIgnoreCase(email);
    }
}