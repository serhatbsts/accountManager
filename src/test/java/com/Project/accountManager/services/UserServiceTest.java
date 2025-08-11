package com.Project.accountManager.services;

import com.Project.accountManager.dto.UserDTO;
import com.Project.accountManager.dto.userRequest.CreateUserRequest;
import com.Project.accountManager.dto.userRequest.UpdateUserRequest;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test");
        user.setSurName("User");
        user.setPassword(123456); // int şifre varsayımı

        createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@example.com");
        createUserRequest.setName("Test");
        createUserRequest.setSurName("User");
        createUserRequest.setPassword(123456); // int şifre varsayımı

        updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("updated@example.com");
        updateUserRequest.setName("Updated");
        updateUserRequest.setSurName("User");
    }

    // convertToDto: alanlar doğru taşınıyor mu?
    @Test
    void testConvertToDto() {
        UserDTO userDTO = userService.convertToDto(user);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getSurName(), userDTO.getSurName());
    }

    // getAllUser: repository listesi dönüyor mu?
    @Test
    void testGetAllUser() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userService.getAllUser();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    // getUserById: kullanıcı bulunuyor mu?
    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.getUserById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals(user.getId(), foundUser.get().getId());
        verify(userRepository, times(1)).findById(1L);
    }

    // login: doğru email/şifre ile kullanıcı döner mi?
    @Test
    void testLogin() {
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(user);
        User loggedInUser = userService.login(user.getEmail(), user.getPassword());
        assertNotNull(loggedInUser);
        assertEquals(user.getEmail(), loggedInUser.getEmail());
        verify(userRepository, times(1)).findByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    // saveOneUser: başarı
    @Test
    void testSaveOneUser_Success() {
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveOneUser(createUserRequest);

        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
        verify(userRepository, times(1)).existsByEmail(createUserRequest.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    // saveOneUser: email zaten kayıtlı -> IllegalArgumentException
    @Test
    void testSaveOneUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveOneUser(createUserRequest);
        });

        assertEquals("This email address is already registered.", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(createUserRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    // updateOneUser: başarı
    @Test
    void testUpdateOneUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateOneUser(1L, updateUserRequest);
        assertNotNull(updatedUser);
        assertEquals(updateUserRequest.getEmail(), updatedUser.getEmail());
        assertEquals(updateUserRequest.getName(), updatedUser.getName());
        assertEquals(updateUserRequest.getSurName(), updatedUser.getSurName());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    // updateOneUser: kullanıcı yok -> null
    @Test
    void testUpdateOneUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User updatedUser = userService.updateOneUser(1L, updateUserRequest);
        assertNull(updatedUser);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    // deleteOneUser: repository çağrılır
    @Test
    void testDeleteOneUser() {
        doNothing().when(userRepository).deleteById(1L);
        assertDoesNotThrow(() -> userService.deleteOneUser(1L));
        verify(userRepository, times(1)).deleteById(1L);
    }
}
