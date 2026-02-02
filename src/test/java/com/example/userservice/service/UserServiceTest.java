package com.example.userservice.service;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.exception.InvalidEmailException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .name("Test User")
                .phoneNumber("1234567890")
                .address("123 Test Street")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    @Test
    void getUserByEmail_ValidEmail_ReturnsUserResponse() {
        // Arrange
        when(userRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Optional.of(testUser));
        
        // Act
        UserResponse result = userService.getUserByEmail("test@example.com");
        
        // Assert
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getName(), result.getName());
        verify(userRepository, times(1)).findByEmailIgnoreCase(anyString());
    }
    
    @Test
    void getUserByEmail_CaseInsensitive_ReturnsUserResponse() {
        // Arrange
        when(userRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Optional.of(testUser));
        
        // Act
        UserResponse result = userService.getUserByEmail("TEST@EXAMPLE.COM");
        
        // Assert
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmailIgnoreCase(anyString());
    }
    
    @Test
    void getUserByEmail_UserNotFound_ThrowsUserNotFoundException() {
        // Arrange
        when(userRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(UserNotFoundException.class, 
                () -> userService.getUserByEmail("notfound@example.com"));
        verify(userRepository, times(1)).findByEmailIgnoreCase(anyString());
    }
    
    @Test
    void getUserByEmail_InvalidEmailFormat_ThrowsInvalidEmailException() {
        // Act & Assert
        assertThrows(InvalidEmailException.class, 
                () -> userService.getUserByEmail("invalid-email"));
        verify(userRepository, never()).findByEmailIgnoreCase(anyString());
    }
    
    @Test
    void getUserByEmail_NullEmail_ThrowsInvalidEmailException() {
        // Act & Assert
        assertThrows(InvalidEmailException.class, 
                () -> userService.getUserByEmail(null));
        verify(userRepository, never()).findByEmailIgnoreCase(anyString());
    }
    
    @Test
    void getUserByEmail_EmptyEmail_ThrowsInvalidEmailException() {
        // Act & Assert
        assertThrows(InvalidEmailException.class, 
                () -> userService.getUserByEmail(""));
        verify(userRepository, never()).findByEmailIgnoreCase(anyString());
    }
}