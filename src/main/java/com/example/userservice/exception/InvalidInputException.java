package com.example.userservice.exception;

/**
 * Exception thrown when invalid input is provided.
 * 
 * @author Automation Engineer
 * @version 1.0.0
 * @since 2024-01-01
 */
public class InvalidInputException extends RuntimeException {

    /**
     * Constructs a new InvalidInputException with the specified detail message.
     * 
     * @param message the detail message
     */
    public InvalidInputException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidInputException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause
     */
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}