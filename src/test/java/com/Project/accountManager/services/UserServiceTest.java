package com.Project.accountManager.services;

import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        User user2 = new User();
        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUser();

        assertEquals(2, result.size());
        assertEquals(user1.getName(), result.get(0).getName());
        assertEquals(user2.getName(), result.get(1).getName());
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertEquals(user.getName(), result.getName());
        assertEquals(user.getSurName(), result.getSurName());
    }

    @Test
    public void testSaveOneUser() {
        User newUser = new User();
        User savedUser = new User();
        when(userRepository.save(newUser)).thenReturn(savedUser);

        User result = userService.saveOneUser(newUser);

        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getName(), result.getName());
        assertEquals(savedUser.getSurName(), result.getSurName());
    }

    @Test
    public void testUpdateOneUser() {
        Long userId = 1L;
        User updatedUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(updatedUser));

        User result = userService.updateOneUser(userId, updatedUser);

        assertEquals(updatedUser.getId(), result.getId());
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getSurName(), result.getSurName());
        assertEquals(updatedUser.getPassword(), result.getPassword());
    }

    @Test
    public void testDeleteOneUser() {
        Long userId = 1L;

        userService.deleteOneUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
