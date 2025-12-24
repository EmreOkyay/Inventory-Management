package com.emre.inventory_management.service.user;

import com.emre.inventory_management.dto.UserDTO;
import com.emre.inventory_management.dto.request.UserRequest;
import com.emre.inventory_management.event.UserEventProducer;
import com.emre.inventory_management.mapper.UserMapper;
import com.emre.inventory_management.model.user.User;
import com.emre.inventory_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserEventProducer producer;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserEventProducer producer, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.producer = producer;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(UserRequest request) {

        if (getUserByEmail(request.getEmail()).isPresent())
            throw new IllegalStateException("User already exists");

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);
        producer.sendUserMailEvent(user);

        return userMapper.toDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    // TODO: Implement a different method instead of Optional
    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email).map(userMapper::toDTO);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
