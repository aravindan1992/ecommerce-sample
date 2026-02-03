package com.example.userservice.controller;

import com.example.userservice.exception.InvalidEmailException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .name("Test User")
                .phone("1234567890")
                .address("123 Test St")
                .city("Test City")
                .state("Test State")
                .zipCode("12345")
                .country("Test Country")
                .build();
    }
    
    @Test
    void getUserByEmail_ValidEmail_ReturnsUser() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString())).thenReturn(testUser);
        
        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.phone").value("1234567890"));
    }
    
    @Test
    void getUserByEmailPath_ValidEmail_ReturnsUser() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString())).thenReturn(testUser);
        
        // Act & Assert
        mockMvc.perform(get("/api/v1/users/{email}", "test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
    }
    
    @Test
    void getUserByEmail_UserNotFound_Returns404() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString()))
                .thenThrow(new UserNotFoundException("User not found with email: notfound@example.com"));
        
        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "notfound@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
    
    @Test
    void getUserByEmail_InvalidEmail_Returns400() throws Exception {
        // Arrange
        when(userService.getUserByEmail(anyString()))
                .thenThrow(new InvalidEmailException("Invalid email format: invalid-email"));
        
        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "invalid-email")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }
}