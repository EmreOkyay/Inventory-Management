package com.emre.inventory_management.service.user;

import com.emre.inventory_management.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    Optional<User> findByEmail(String email);
}
