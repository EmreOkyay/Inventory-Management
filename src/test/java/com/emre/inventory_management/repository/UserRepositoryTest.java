package com.emre.inventory_management.repository;

import com.emre.inventory_management.model.user.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetUserByEmail_Found() {
        User user = new User();
        user.setFirstName("Emre");
        user.setLastName("Okyay");
        user.setEmail("emre@okyay.com");

        userRepository.save(user);

        Optional<User> result = userRepository.getUserByEmail("emre@okyay.com");

        assertTrue(result.isPresent());
        assertEquals("Emre", result.get().getFirstName());
    }

    @Test
    void testGetUserByEmail_NotFound() {
        Optional<User> result = userRepository.getUserByEmail("notfound@test.com");

        assertFalse(result.isPresent());
    }
}
