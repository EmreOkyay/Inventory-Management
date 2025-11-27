package com.emre.inventory_management.service.user;

import com.emre.inventory_management.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getUserById(Long id);
    Optional<UserDTO> getUserByEmail(String email);
}
