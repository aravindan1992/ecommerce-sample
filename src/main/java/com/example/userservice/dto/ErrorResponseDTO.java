package com.example.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for error responses.
 * Provides structured error information to clients.
 * 
 * @author Automation Engineer
 * @version 1.0.0
 * @since 2024-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDTO {

    /**
     * HTTP status code of the error.
     */
    private int status;

    /**
     * Error message describing what went wrong.
     */
    private String message;

    /**
     * List of detailed error messages (e.g., validation errors).
     */
    private List<String> errors;

    /**
     * Timestamp when the error occurred.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Request path where the error occurred.
     */
    private String path;
}