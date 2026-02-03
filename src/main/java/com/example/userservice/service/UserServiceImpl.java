package com.example.userservice.service;

import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.exception.InvalidInputException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of UserService interface.
 * Provides business logic for user operations including name-based search.
 * 
 * @author Automation Engineer
 * @version 1.0.0
 * @since 2024-01-01
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructor for dependency injection.
     * 
     * @param userRepository the user repository
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserResponseDTO> findUsersByName(String name) {
        log.info("Searching for users with name containing: {}", name);
        
        // Validate input
        validateNameInput(name);
        
        // Trim and sanitize input
        String sanitizedName = name.trim();
        
        // Search for users
        List<User> users = userRepository.findByNameContainingIgnoreCase(sanitizedName);
        
        log.info("Found {} users matching name: {}", users.size(), sanitizedName);
        
        // Convert to DTOs
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserResponseDTO> findActiveUsersByName(String name) {
        log.info("Searching for active users with name containing: {}", name);
        
        // Validate input
        validateNameInput(name);
        
        // Trim and sanitize input
        String sanitizedName = name.trim();
        
        // Search for active users
        List<User> users = userRepository.findActiveUsersByName(sanitizedName);
        
        log.info("Found {} active users matching name: {}", users.size(), sanitizedName);
        
        // Convert to DTOs
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserResponseDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        
        if (id == null || id <= 0) {
            log.error("Invalid user ID: {}", id);
            throw new InvalidInputException("User ID must be a positive number");
        }
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found with ID: " + id);
                });
        
        log.info("Successfully fetched user with ID: {}", id);
        return convertToDTO(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");
        
        List<User> users = userRepository.findAll();
        
        log.info("Found {} users in total", users.size());
        
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Validates the name input parameter.
     * 
     * @param name the name to validate
     * @throws InvalidInputException if name is invalid
     */
    private void validateNameInput(String name) {
        if (StringUtils.isBlank(name)) {
            log.error("Name parameter is blank or null");
            throw new InvalidInputException("Name cannot be blank or null");
        }
        
        if (name.trim().length() < 1) {
            log.error("Name parameter is too short: {}", name);
            throw new InvalidInputException("Name must be at least 1 character long");
        }
        
        if (name.trim().length() > 100) {
            log.error("Name parameter is too long: {} characters", name.length());
            throw new InvalidInputException("Name must not exceed 100 characters");
        }
    }

    /**
     * Converts a User entity to UserResponseDTO.
     * 
     * @param user the user entity
     * @return the user response DTO
     */
    private UserResponseDTO convertToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .department(user.getDepartment())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}