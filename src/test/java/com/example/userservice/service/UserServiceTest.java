package com.example.userservice.service;

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
                .phone("+1234567890")
                .address("123 Test Street")
                .city("Test City")
                .country("Test Country")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    @Test
    void getUserByEmail_ValidEmail_ReturnsUser() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(testUser));
        
        // Act
        User result = userService.getUserByEmail(email);
        
        // Assert
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getName(), result.getName());
        verify(userRepository, times(1)).findByEmailIgnoreCase(email);
    }
    
    @Test
    void getUserByEmail_CaseInsensitive_ReturnsUser() {
        // Arrange
        String email = "TEST@EXAMPLE.COM";
        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(testUser));
        
        // Act
        User result = userService.getUserByEmail(email);
        
        // Assert
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmailIgnoreCase(email);
    }
    
    @Test
    void getUserByEmail_UserNotFound_ThrowsException() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(email));
        verify(userRepository, times(1)).findByEmailIgnoreCase(email);
    }
    
    @Test
    void getUserByEmail_NullEmail_ThrowsException() {
        // Act & Assert
        assertThrows(InvalidEmailException.class, () -> userService.getUserByEmail(null));
        verify(userRepository, never()).findByEmailIgnoreCase(anyString());
    }
    
    @Test
    void getUserByEmail_EmptyEmail_ThrowsException() {
        // Act & Assert
        assertThrows(InvalidEmailException.class, () -> userService.getUserByEmail(""));
        verify(userRepository, never()).findByEmailIgnoreCase(anyString());
    }
    
    @Test
    void getUserByEmail_InvalidEmailFormat_ThrowsException() {
        // Arrange
        String invalidEmail = "invalid-email";
        
        // Act & Assert
        assertThrows(InvalidEmailException.class, () -> userService.getUserByEmail(invalidEmail));
        verify(userRepository, never()).findByEmailIgnoreCase(anyString());
    }
    
    @Test
    void getUserByEmail_EmailWithSpaces_TrimsAndReturnsUser() {
        // Arrange
        String emailWithSpaces = "  test@example.com  ";
        when(userRepository.findByEmailIgnoreCase(emailWithSpaces.trim())).thenReturn(Optional.of(testUser));
        
        // Act
        User result = userService.getUserByEmail(emailWithSpaces);
        
        // Assert
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmailIgnoreCase(emailWithSpaces.trim());
    }
    
    @Test
    void userExists_ExistingEmail_ReturnsTrue() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.existsByEmailIgnoreCase(email)).thenReturn(true);
        
        // Act
        boolean result = userService.userExists(email);
        
        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).existsByEmailIgnoreCase(email);
    }
    
    @Test
    void userExists_NonExistingEmail_ReturnsFalse() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.existsByEmailIgnoreCase(email)).thenReturn(false);
        
        // Act
        boolean result = userService.userExists(email);
        
        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).existsByEmailIgnoreCase(email);
    }
}