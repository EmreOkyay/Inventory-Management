package com.emre.inventory_management.service.user;

import com.emre.inventory_management.dto.request.UserRequest;
import com.emre.inventory_management.dto.UserDTO;
import com.emre.inventory_management.mapper.UserMapper;
import com.emre.inventory_management.model.user.User;
import com.emre.inventory_management.repository.UserRepository;
import com.emre.inventory_management.event.UserEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserEventProducer producer;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setFirstName("Emre");
        user.setLastName("Okyay");
        user.setEmail("emre@okyay.com");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("Emre");
        userDTO.setLastName("Okyay");
        userDTO.setEmail("emre@okyay.com");
    }

    @Test
    void testCreateUser_AlreadyExists() {
        when(userRepository.getUserByEmail("emre@okyay.com")).thenReturn(Optional.of(user));
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        UserRequest request = new UserRequest();
        request.setFirstName("Emre");
        request.setLastName("Okyay");
        request.setEmail("emre@okyay.com");

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> userService.createUser(request)
        );

        assertEquals("User already exists", exception.getMessage());

        verify(producer, never()).sendUserMailEvent(any());
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.getUserByEmail("emre@okyay.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserRequest request = new UserRequest();
        request.setFirstName("Emre");
        request.setLastName("Okyay");
        request.setEmail("emre@okyay.com");
        UserDTO result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        verify(userRepository, times(1)).save(any(User.class));
        verify(producer, times(1)).sendUserMailEvent(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        List<UserDTO> users = userService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("Emre", users.get(0).getFirstName());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        Optional<UserDTO> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("Emre", result.get().getFirstName());
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.getUserByEmail("emre@okyay.com")).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        Optional<UserDTO> result = userService.getUserByEmail("emre@okyay.com");
        assertTrue(result.isPresent());
        assertEquals("Emre", result.get().getFirstName());
    }

    @Test
    void testDeleteById() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
