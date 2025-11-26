package com.emre.inventory_management.service.user;

import com.emre.inventory_management.dto.UserDTO;
import com.emre.inventory_management.model.User;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getUserById(Long id);
    Optional<UserDTO> findByEmail(String email);
}
