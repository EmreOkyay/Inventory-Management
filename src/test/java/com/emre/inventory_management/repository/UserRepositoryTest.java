package com.emre.inventory_management.repository;

import com.emre.inventory_management.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = com.emre.inventory_management.InventoryManagementApplication.class,
        properties = {
                "spring.kafka.enabled=false",
                "spring.mail.enabled=false",
                "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "spring.datasource.driverClassName=org.h2.Driver",
                "spring.datasource.username=sa",
                "spring.datasource.password="
        }
)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetUserByEmail_Found() {
        User user = new User();
        user.setEmail("test@mail.com");
        userRepository.save(user);

        Optional<User> found = userRepository.getUserByEmail("test@mail.com");
        assertTrue(found.isPresent());
    }
}

