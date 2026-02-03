package com.example.userservice.service;

import com.example.userservice.dto.UserResponseDTO;

import java.util.List;

/**
 * Service interface for user operations.
 * Defines business logic methods for user management.
 * 
 * @author Automation Engineer
 * @version 1.0.0
 * @since 2024-01-01
 */
public interface UserService {

    /**
     * Searches for users by name using case-insensitive partial matching.
     * 
     * @param name the name or partial name to search for
     * @return list of users matching the search criteria
     * @throws com.example.userservice.exception.InvalidInputException if name is invalid
     */
    List<UserResponseDTO> findUsersByName(String name);

    /**
     * Searches for active users by name using case-insensitive partial matching.
     * 
     * @param name the name or partial name to search for
     * @return list of active users matching the search criteria
     * @throws com.example.userservice.exception.InvalidInputException if name is invalid
     */
    List<UserResponseDTO> findActiveUsersByName(String name);

    /**
     * Retrieves a user by their unique identifier.
     * 
     * @param id the user ID
     * @return the user details
     * @throws com.example.userservice.exception.UserNotFoundException if user not found
     */
    UserResponseDTO getUserById(Long id);

    /**
     * Retrieves all users in the system.
     * 
     * @return list of all users
     */
    List<UserResponseDTO> getAllUsers();
}