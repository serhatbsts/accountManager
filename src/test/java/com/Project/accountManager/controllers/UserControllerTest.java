package com.Project.accountManager.controllers;

import com.Project.accountManager.dto.UserDTO;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User();
        newUser.setName("Serhat");
        newUser.setSurName("Bestas");

        when(userService.saveOneUser(any(User.class))).thenReturn(newUser);

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content("{\"name\":\"Serhat\",\"surName\":\"Bestas\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Serhat"))
                .andExpect(jsonPath("$.surName").value("Bestas"));
    }

    @Test
    public void testGetOneUser() throws Exception {
        User user = new User();
        user.setName("Serhat");
        user.setSurName("Bestas");

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Serhat");
        userDTO.setSurName("Bestas");

        when(userService.getUserById(1L)).thenReturn(user);
        when(userService.convertToDto(user)).thenReturn(userDTO);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Serhat"))
                .andExpect(jsonPath("$.surName").value("Bestas"));
    }

    @Test
    public void testUpdateOneUser() throws Exception {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Serhat");
        existingUser.setSurName("Bestas");

        User updatedUser = new User();
        updatedUser.setName("Serkan");
        updatedUser.setSurName("Bestas");

        when(userService.updateOneUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                        .contentType("application/json")
                        .content("{\"name\":\"Serkan\",\"surName\":\"Bestas\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Serkan"));
    }

    @Test
    public void testDeleteOneUser() throws Exception {
        doNothing().when(userService).deleteOneUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteOneUser(1L);
    }
}
