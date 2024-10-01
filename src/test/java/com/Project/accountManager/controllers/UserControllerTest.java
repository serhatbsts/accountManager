package com.Project.accountManager.controllers;

import com.Project.accountManager.dto.UserDTO;
import com.Project.accountManager.dto.userRequest.CreateUserRequest;
import com.Project.accountManager.dto.userRequest.LoginUserRequest;
import com.Project.accountManager.dto.userRequest.UpdateUserRequest;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test");
        user.setSurName("User");

        when(userService.getAllUser()).thenReturn(Collections.singletonList(user));

        assertEquals(1, userController.getAllUsers().size());
        verify(userService, times(1)).getAllUser();
    }

    @Test
    void createUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@example.com");
        request.setName("Test");
        request.setSurName("User");
        request.setPassword(1234);

        User user = new User();
        user.setId(1L);
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setSurName(request.getSurName());
        user.setPassword(request.getPassword());

        when(userService.saveOneUser(request)).thenReturn(user);

        User createdUser = userController.createUser(request);

        assertNotNull(createdUser);
        assertEquals("test@example.com", createdUser.getEmail());
        verify(userService, times(1)).saveOneUser(request);
    }

    @Test
    void checkEmail_Exists() {
        String email = "test@example.com";

        when(userService.isEmailRegistered(email)).thenReturn(true);

        ResponseEntity<Void> response = userController.checkEmail(Collections.singletonMap("email", email));

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void checkEmail_NotExists() {
        String email = "test@example.com";

        when(userService.isEmailRegistered(email)).thenReturn(false);

        ResponseEntity<Void> response = userController.checkEmail(Collections.singletonMap("email", email));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void login_Success() {

        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("test@example.com");
        request.setPassword(1234);


        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test");
        user.setSurName("User");
        user.setPassword(1234);


        when(userService.login(request.getEmail(), request.getPassword())).thenReturn(user);


        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setId(1L);
        expectedUserDTO.setEmail("test@example.com");
        expectedUserDTO.setName("Test");
        expectedUserDTO.setSurName("User");

        when(userService.convertToDto(user)).thenReturn(expectedUserDTO);

        ResponseEntity<UserDTO> response = userController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals("test@example.com", response.getBody().getEmail());
        assertEquals("Test", response.getBody().getName());
        assertEquals("User", response.getBody().getSurName());
    }






    @Test
    void login_Failure() {
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("wrong@example.com");
        request.setPassword(1234);

        when(userService.login(request.getEmail(), request.getPassword())).thenReturn(null);

        ResponseEntity<UserDTO> response = userController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getUserById_Success() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        user.setName("Test");
        user.setSurName("User");


        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setName("Test");
        userDTO.setSurName("User");


        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(userService.convertToDto(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getEmail());
        assertEquals("Test", response.getBody().getName());
        assertEquals("User", response.getBody().getSurName());
    }



    @Test
    void getUserById_NotFound() {
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateUser_Success() {
        Long userId = 1L;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("updated@example.com");
        updateUserRequest.setName("Updated");
        updateUserRequest.setSurName("User");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setEmail(updateUserRequest.getEmail());
        updatedUser.setName(updateUserRequest.getName());
        updatedUser.setSurName(updateUserRequest.getSurName());

        when(userService.updateOneUser(userId, updateUserRequest)).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser(userId, updateUserRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("updated@example.com", response.getBody().getEmail());
    }

    @Test
    void updateUser_NotFound() {
        Long userId = 1L;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("updated@example.com");
        updateUserRequest.setName("Updated");
        updateUserRequest.setSurName("User");

        when(userService.updateOneUser(userId, updateUserRequest)).thenReturn(null);

        ResponseEntity<User> response = userController.updateUser(userId, updateUserRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteUser_Success() {
        Long userId = 1L;

        doNothing().when(userService).deleteOneUser(userId);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }

    @Test
    void deleteUser_Failure() {
        Long userId = 1L;

        doThrow(new RuntimeException("Deletion failed")).when(userService).deleteOneUser(userId);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to delete user", response.getBody());
    }
}
