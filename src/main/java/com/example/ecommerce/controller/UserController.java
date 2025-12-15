package com.example.ecommerce.controller;

import com.example.ecommerce.dto.UserRequest;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest request) {
        return service.createUser(request);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        return service.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return service.deleteUser(id) ? "User deleted" : "User not found";
    }
}
