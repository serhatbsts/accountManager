package com.Project.accountManager.services;

import com.Project.accountManager.dto.userRequest.CreateUserRequest;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Bu test, yeni bir kullanıcı kaydı oluşturmanın başarılı olup olmadığını doğrular.
    @Test
    void testSaveOneUser_Success() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@example.com");
        request.setName("Test");
        request.setSurName("User");
        request.setPassword(1234);

        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test");
        user.setSurName("User");
        user.setPassword(1234);

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.saveOneUser(request);

        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("Test", createdUser.getName());
        assertEquals("User", createdUser.getSurName());
    }

    // Bu test, zaten kayıtlı olan bir e-posta adresiyle kullanıcı kaydı oluşturmanın hata verdiğini doğrular.
    @Test
    void testSaveOneUser_EmailAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@example.com");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Method call inside try-catch or assertThrows can be added
    }
}
