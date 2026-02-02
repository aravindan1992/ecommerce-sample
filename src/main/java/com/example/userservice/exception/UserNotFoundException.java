package com.example.userservice.exception;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String email) {
        super(String.format("User not found with email: %s", email));
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}