package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserServiceImpl.
 * 
 * @author Automation Engineer
 * @version 1.0.0
 * @since 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser1;
    private User testUser2;

    @BeforeEach
    void setUp() {
        testUser1 = new User();
        testUser1.setId(1L);
        testUser1.setName("John Doe");
        testUser1.setEmail("john.doe@example.com");

        testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setName("Jane Smith");
        testUser2.setEmail("jane.smith@example.com");
    }

    @Test
    void findUserByName_ReturnsUser_WhenUserExists() {
        // Arrange
        when(userRepository.findByName("John Doe")).thenReturn(Optional.of(testUser1));

        // Act
        Optional<User> result = userService.findUserByName("John Doe");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        assertEquals("john.doe@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findByName("John Doe");
    }

    @Test
    void findUserByName_ReturnsEmpty_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByName("Nonexistent User")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findUserByName("Nonexistent User");

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByName("Nonexistent User");
    }

    @Test
    void findUserByName_HandlesNullName() {
        // Arrange
        when(userRepository.findByName(null)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findUserByName(null);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByName(null);
    }

    @Test
    void getAllUsers_ReturnsListOfUsers_WhenUsersExist() {
        // Arrange
        List<User> users = Arrays.asList(testUser1, testUser2);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsers_ReturnsEmptyList_WhenNoUsersExist() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void createUser_SavesAndReturnsUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(testUser1);

        // Act
        User result = userService.createUser(testUser1);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(userRepository, times(1)).save(testUser1);
    }

    @Test
    void findUserById_ReturnsUser_WhenUserExists() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser1));

        // Act
        Optional<User> result = userService.findUserById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("John Doe", result.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findUserById_ReturnsEmpty_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findUserById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(999L);
    }
}
