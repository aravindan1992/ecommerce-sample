package com.example.userservice.controller;

import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.exception.InvalidInputException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for UserController.
 * 
 * @author Automation Engineer
 * @version 1.0.0
 * @since 2024-01-01
 */
@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserResponseDTO userResponse1;
    private UserResponseDTO userResponse2;

    @BeforeEach
    void setUp() {
        userResponse1 = UserResponseDTO.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .department("Engineering")
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userResponse2 = UserResponseDTO.builder()
                .id(2L)
                .name("John Smith")
                .email("john.smith@example.com")
                .phone("0987654321")
                .department("Marketing")
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void searchUsersByName_WithValidName_ReturnsUsers() throws Exception {
        // Arrange
        List<UserResponseDTO> users = Arrays.asList(userResponse1, userResponse2);
        when(userService.findUsersByName("John")).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[1].name", is("John Smith")));

        verify(userService, times(1)).findUsersByName("John");
    }

    @Test
    void searchUsersByName_WithNoResults_ReturnsEmptyList() throws Exception {
        // Arrange
        when(userService.findUsersByName("NonExistent")).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "NonExistent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(userService, times(1)).findUsersByName("NonExistent");
    }

    @Test
    void searchUsersByName_WithBlankName_ReturnsBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(userService, never()).findUsersByName(anyString());
    }

    @Test
    void searchUsersByName_WithMissingParameter_ReturnsBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/users/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(userService, never()).findUsersByName(anyString());
    }

    @Test
    void searchUsersByName_WithInvalidInput_ReturnsBadRequest() throws Exception {
        // Arrange
        when(userService.findUsersByName(anyString()))
                .thenThrow(new InvalidInputException("Name must be at least 1 character long"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Name must be at least 1 character long")));
    }

    @Test
    void searchActiveUsersByName_WithValidName_ReturnsActiveUsers() throws Exception {
        // Arrange
        List<UserResponseDTO> users = Arrays.asList(userResponse1);
        when(userService.findActiveUsersByName("John")).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/search/active")
                        .param("name", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].status", is("ACTIVE")));

        verify(userService, times(1)).findActiveUsersByName("John");
    }

    @Test
    void getUserById_WithValidId_ReturnsUser() throws Exception {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(userResponse1);

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getUserById_WithInvalidId_ReturnsNotFound() throws Exception {
        // Arrange
        when(userService.getUserById(999L))
                .thenThrow(new UserNotFoundException("User not found with ID: 999"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("User not found with ID: 999")));
    }

    @Test
    void getAllUsers_ReturnsAllUsers() throws Exception {
        // Arrange
        List<UserResponseDTO> users = Arrays.asList(userResponse1, userResponse2);
        when(userService.getAllUsers()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void healthCheck_ReturnsOk() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/users/health")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User Service is running"));
    }
}