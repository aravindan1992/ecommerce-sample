package com.example.userservice.controller;

import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for user operations.
 * Provides endpoints for searching and retrieving user information.
 * 
 * @author Automation Engineer
 * @version 1.0.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/v1/users")
@Validated
@Slf4j
@Tag(name = "User Management", description = "APIs for user search and retrieval operations")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for dependency injection.
     * 
     * @param userService the user service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Searches for users by name using case-insensitive partial matching.
     * 
     * @param name the name or partial name to search for
     * @return ResponseEntity containing list of matching users
     */
    @Operation(
        summary = "Search users by name",
        description = "Searches for users by name using case-insensitive partial matching. Returns all users whose names contain the search term."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved users",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input parameter"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchUsersByName(
            @Parameter(description = "Name or partial name to search for", required = true)
            @RequestParam("name") 
            @NotBlank(message = "Name parameter cannot be blank") 
            String name) {
        
        log.info("Received request to search users by name: {}", name);
        
        List<UserResponseDTO> users = userService.findUsersByName(name);
        
        if (users.isEmpty()) {
            log.info("No users found matching name: {}", name);
            return ResponseEntity.ok(users);
        }
        
        log.info("Returning {} users matching name: {}", users.size(), name);
        return ResponseEntity.ok(users);
    }

    /**
     * Searches for active users by name using case-insensitive partial matching.
     * 
     * @param name the name or partial name to search for
     * @return ResponseEntity containing list of matching active users
     */
    @Operation(
        summary = "Search active users by name",
        description = "Searches for active users by name using case-insensitive partial matching. Returns only users with ACTIVE status."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved active users",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input parameter"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @GetMapping("/search/active")
    public ResponseEntity<List<UserResponseDTO>> searchActiveUsersByName(
            @Parameter(description = "Name or partial name to search for", required = true)
            @RequestParam("name") 
            @NotBlank(message = "Name parameter cannot be blank") 
            String name) {
        
        log.info("Received request to search active users by name: {}", name);
        
        List<UserResponseDTO> users = userService.findActiveUsersByName(name);
        
        if (users.isEmpty()) {
            log.info("No active users found matching name: {}", name);
            return ResponseEntity.ok(users);
        }
        
        log.info("Returning {} active users matching name: {}", users.size(), name);
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a user by their unique identifier.
     * 
     * @param id the user ID
     * @return ResponseEntity containing the user details
     */
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a user by their unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved user",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid user ID"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "User ID", required = true)
            @PathVariable("id") Long id) {
        
        log.info("Received request to get user by ID: {}", id);
        
        UserResponseDTO user = userService.getUserById(id);
        
        log.info("Successfully retrieved user with ID: {}", id);
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves all users in the system.
     * 
     * @return ResponseEntity containing list of all users
     */
    @Operation(
        summary = "Get all users",
        description = "Retrieves all users in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all users",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("Received request to get all users");
        
        List<UserResponseDTO> users = userService.getAllUsers();
        
        log.info("Returning {} users", users.size());
        return ResponseEntity.ok(users);
    }

    /**
     * Health check endpoint.
     * 
     * @return ResponseEntity with health status
     */
    @Operation(
        summary = "Health check",
        description = "Checks if the service is running"
    )
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("User Service is running");
    }
}