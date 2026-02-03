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
        
        User user = User.builder()
                .email("integration@example.com")
                .name("Integration Test User")
                .phone("9876543210")
                .address("456 Integration Ave")
                .city("Integration City")
                .state("Integration State")
                .zipCode("54321")
                .country("Integration Country")
                .build();
        
        userRepository.save(user);
    }
    
    @Test
    void getUserByEmail_IntegrationTest_Success() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "integration@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("integration@example.com"))
                .andExpect(jsonPath("$.name").value("Integration Test User"))
                .andExpect(jsonPath("$.phone").value("9876543210"));
    }
    
    @Test
    void getUserByEmail_CaseInsensitive_IntegrationTest_Success() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "INTEGRATION@EXAMPLE.COM")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("integration@example.com"))
                .andExpect(jsonPath("$.name").value("Integration Test User"));
    }
    
    @Test
    void getUserByEmail_NotFound_IntegrationTest_Returns404() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .param("email", "notfound@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found with email: notfound@example.com"));
    }
    
    @Test
    void getUserByEmailPath_IntegrationTest_Success() throws Exception {
        mockMvc.perform(get("/api/v1/users/{email}", "integration@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("integration@example.com"))
                .andExpect(jsonPath("$.name").value("Integration Test User"));
    }
}