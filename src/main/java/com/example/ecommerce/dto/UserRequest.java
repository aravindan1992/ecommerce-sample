package com.example.ecommerce.dto;

public class UserRequest {
    private String name;
    private String email;
    private String role;
    private boolean active;

    public UserRequest() {}

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public boolean isActive() { return active; }
}
