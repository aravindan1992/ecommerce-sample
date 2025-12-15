package com.example.ecommerce.service;

import com.example.ecommerce.dto.UserRequest;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User createUser(UserRequest request) {
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getRole(),
                request.isActive()
        );
        return repository.save(user);
    }

    public User updateUser(Long id, UserRequest request) {
        User user = repository.findById(id).orElse(null);
        if (user == null) return null;

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setActive(request.isActive());

        return repository.save(user);
    }

    public boolean deleteUser(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
