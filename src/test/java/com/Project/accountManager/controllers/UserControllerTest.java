package com.Project.accountManager.controllers;

import com.Project.accountManager.dto.UserDTO;
import com.Project.accountManager.dto.userRequest.CreateUserRequest;
import com.Project.accountManager.dto.userRequest.LoginUserRequest;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Bu test, geçerli bir kullanıcıyla başarılı giriş yapmayı kontrol eder.
    @Test
    void testLogin_Success() {
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("test@example.com");
        request.setPassword(1234);

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("test@example.com");

        when(userService.login(request.getEmail(), request.getPassword())).thenReturn(user);
        when(userService.convertToDto(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    // Bu test, geçersiz bir giriş yapmaya çalışıldığında doğru hatanın döndüğünü kontrol eder.
    @Test
    void testLogin_InvalidCredentials() {
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("test@example.com");
        request.setPassword(1234);

        when(userService.login(request.getEmail(), request.getPassword())).thenReturn(null);

        ResponseEntity<UserDTO> response = userController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
