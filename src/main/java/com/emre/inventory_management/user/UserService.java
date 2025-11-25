package com.emre.inventory_management.user;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
