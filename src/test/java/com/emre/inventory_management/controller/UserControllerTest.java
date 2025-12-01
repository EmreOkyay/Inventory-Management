package com.emre.inventory_management.controller;

import com.emre.inventory_management.dto.UserDTO;
import com.emre.inventory_management.dto.request.UserRequest;
import com.emre.inventory_management.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("Emre");
        userDTO.setLastName("Okyay");
        userDTO.setEmail("emre@okyay.com");
    }

    @Test
    void testAddUser() {
        UserRequest request = new UserRequest();
        request.setEmail("emre@okyay.com");

        when(userServiceImpl.createUser(request)).thenReturn(userDTO);

        UserDTO response = userController.addUser(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(userServiceImpl, times(1)).createUser(request);
    }

    @Test
    void testGetAllUsers() {
        when(userServiceImpl.getAllUsers()).thenReturn(List.of(userDTO));

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(userServiceImpl, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_Found() {
        when(userServiceImpl.getUserById(1L)).thenReturn(Optional.of(userDTO));

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertNotNull(response.getBody());
        assertEquals("Emre", response.getBody().getFirstName());
        verify(userServiceImpl).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userServiceImpl.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertNull(response.getBody());
        verify(userServiceImpl).getUserById(1L);
    }

    @Test
    void testGetUserByEmail_Found() {
        when(userServiceImpl.getUserByEmail("emre@okyay.com"))
                .thenReturn(Optional.of(userDTO));

        ResponseEntity<UserDTO> response = userController.getUserByEmail("emre@okyay.com");

        assertNotNull(response.getBody());
        assertEquals("Emre", response.getBody().getFirstName());
        verify(userServiceImpl).getUserByEmail("emre@okyay.com");
    }

    @Test
    void testGetUserByEmail_NotFound() {
        when(userServiceImpl.getUserByEmail("emre@okyay.com"))
                .thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response =
                userController.getUserByEmail("emre@okyay.com");

        assertNull(response.getBody());
        verify(userServiceImpl).getUserByEmail("emre@okyay.com");
    }

    @Test
    void testDeleteUserById() {
        doNothing().when(userServiceImpl).deleteById(1L);

        userController.deleteUserById(1L);

        verify(userServiceImpl, times(1)).deleteById(1L);
    }
}

