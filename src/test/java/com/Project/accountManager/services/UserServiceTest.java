package com.Project.accountManager.services;

import com.Project.accountManager.dto.UserDTO;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConvertToDto() {
        User user = new User();
        user.setName("Serhat");
        user.setSurName("Bestas");

        UserDTO userDTO = userService.convertToDto(user);

        assertNotNull(userDTO);
        assertEquals("Serhat", userDTO.getName());
        assertEquals("Bestas", userDTO.getSurName());
    }

    @Test
    public void testSaveOneUser() {
        User user = new User();
        user.setName("Serhat");
        user.setSurName("Bestas");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveOneUser(user);

        assertNotNull(savedUser);
        assertEquals("Serhat", savedUser.getName());
    }

    @Test
    public void testUpdateOneUser_Success() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Serhat");
        existingUser.setSurName("Bestas");

        User updatedUser = new User();
        updatedUser.setName("Serkan");
        updatedUser.setSurName("Bestas");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateOneUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("Serkan", result.getName());
    }

    @Test
    public void testUpdateOneUser_NotFound() {
        User user = new User();
        user.setName("Serkan");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User result = userService.updateOneUser(1L, user);

        assertNull(result);
    }

    @Test
    public void testDeleteOneUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteOneUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
