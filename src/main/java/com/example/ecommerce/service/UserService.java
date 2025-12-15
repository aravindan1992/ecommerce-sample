package com.example.ecommerce.service;

import com.example.ecommerce.dto.UserRequest;
import com.example.ecommerce.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(UserRequest request);
    User updateUser(Long id, UserRequest request);
    boolean deleteUser(Long id);
}
