package com.Project.accountManager.services;

import com.Project.accountManager.dto.UserDTO;
import com.Project.accountManager.dto.userRequest.CreateUserRequest;
import com.Project.accountManager.dto.userRequest.UpdateUserRequest;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private CreateUserRequest createUserRequest;
    private UpdateUserRequest updateUserRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test");
        user.setSurName("User");
        user.setPassword(123456); // Şifre int olarak tanımlandı

        createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@example.com");
        createUserRequest.setName("Test");
        createUserRequest.setSurName("User");
        createUserRequest.setPassword(123456); // Şifre int olarak tanımlandı

        updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("updated@example.com");
        updateUserRequest.setName("Updated");
        updateUserRequest.setSurName("User");
    }

    @Test
    void testConvertToDto() {
        UserDTO userDTO = userService.convertToDto(user);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getSurName(), userDTO.getSurName());
    }

    @Test
    void testGetAllUser() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userService.getAllUser();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.getUserById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals(user.getId(), foundUser.get().getId());
    }

    @Test
    void testLogin() {
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(user);
        User loggedInUser = userService.login(user.getEmail(), user.getPassword());
        assertNotNull(loggedInUser);
        assertEquals(user.getEmail(), loggedInUser.getEmail());
    }

    @Test
    void testSaveOneUser_Success() {
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveOneUser(createUserRequest);
        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void testSaveOneUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveOneUser(createUserRequest);
        });

        assertEquals("This email address is already registered.", exception.getMessage());
    }

    @Test
    void testUpdateOneUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateOneUser(1L, updateUserRequest);
        assertNotNull(updatedUser);
        assertEquals(updateUserRequest.getEmail(), updatedUser.getEmail());
    }

    @Test
    void testUpdateOneUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User updatedUser = userService.updateOneUser(1L, updateUserRequest);
        assertNull(updatedUser);
    }

    @Test
    void testDeleteOneUser() {
        doNothing().when(userRepository).deleteById(1L);
        assertDoesNotThrow(() -> userService.deleteOneUser(1L));
        verify(userRepository, times(1)).deleteById(1L);
    }
}
