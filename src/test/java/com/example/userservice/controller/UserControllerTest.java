package com.example.userservice.controller;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.exception.InvalidEmailException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private UserService userService;
    
    private UserResponse testUserResponse;
    
    @BeforeEach
    void setUp() {
        testUserResponse = UserResponse.builder()
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
    void getUserByEmail_ValidEmail_ReturnsOk() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString()))
                .thenReturn(testUserResponse);
        
        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
    }
    
    @Test
    void getUserByEmail_UserNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString()))
                .thenThrow(new UserNotFoundException("notfound@example.com"));
        
        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "notfound@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
    
    @Test
    void getUserByEmail_InvalidEmail_ReturnsBadRequest() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString()))
                .thenThrow(new InvalidEmailException("invalid-email"));
        
        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "invalid-email")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
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