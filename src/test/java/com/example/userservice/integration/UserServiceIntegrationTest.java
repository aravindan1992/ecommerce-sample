package com.example.userservice.integration;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        
        User testUser = User.builder()
                .email("integration@example.com")
                .name("Integration Test User")
                .phoneNumber("9876543210")
                .address("456 Integration Ave")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        userRepository.save(testUser);
    }
    
    @Test
    void getUserByEmail_ExistingUser_ReturnsUserDetails() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "integration@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("integration@example.com"))
                .andExpect(jsonPath("$.name").value("Integration Test User"))
                .andExpect(jsonPath("$.phoneNumber").value("9876543210"));
    }
    
    @Test
    void getUserByEmail_CaseInsensitive_ReturnsUserDetails() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "INTEGRATION@EXAMPLE.COM")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("integration@example.com"));
    }
    
    @Test
    void getUserByEmail_NonExistingUser_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "nonexistent@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
    
    @Test
    void getUserByEmail_InvalidEmailFormat_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "invalid-email-format")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}