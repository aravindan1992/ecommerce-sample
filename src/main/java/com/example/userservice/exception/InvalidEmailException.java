package com.example.userservice.exception;

public class InvalidEmailException extends RuntimeException {
    
    public InvalidEmailException(String email) {
        super(String.format("Invalid email format: %s", email));
    }
    
    public InvalidEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}