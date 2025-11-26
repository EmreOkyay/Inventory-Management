package com.emre.inventory_management.service.user;

import com.emre.inventory_management.dto.UserDTO;
import com.emre.inventory_management.dto.UserRequest;
import com.emre.inventory_management.event.UserEventProducer;
import com.emre.inventory_management.model.User;
import com.emre.inventory_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserEventProducer producer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserEventProducer producer) {
        this.userRepository = userRepository;
        this.producer = producer;
    }

    public User createUser(UserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        producer.sendUserEvent("user_created:" + user.getFirstName());

        return userRepository.save(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(users -> {
            UserDTO userDto = new UserDTO();
            userDto.setId(users.getId());
            userDto.setFirstName(users.getFirstName());
            userDto.setLastName(users.getLastName());
            userDto.setEmail(users.getEmail());

            return userDto;
        }).toList();
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(user -> {
            UserDTO userDto = new UserDTO();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());

            return userDto;
        });
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(user -> {
            UserDTO userDto = new UserDTO();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());

            return userDto;
        });
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // TODO: Add exception handling
}
