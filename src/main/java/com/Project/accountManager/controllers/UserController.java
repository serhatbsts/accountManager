package com.Project.accountManager.controllers;

import com.Project.accountManager.dto.UserDTO;
import com.Project.accountManager.dto.userRequest.CreateUserRequest;
import com.Project.accountManager.dto.userRequest.LoginUserRequest;
import com.Project.accountManager.dto.userRequest.UpdateUserRequest;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody CreateUserRequest newUser) {
        return userService.saveOneUser(newUser);
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<Void> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean emailExists = userService.isEmailRegistered(email);
        if (emailExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginUserRequest loginRequest){
        User loggedInUser=userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (loggedInUser!=null){
            UserDTO userDTO = userService.convertToDto(loggedInUser);
            return ResponseEntity.ok(userDTO);
        }else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        Optional<User> user=userService.getUserById(userId);
        if(user.isPresent()){
            UserDTO userDTO=userService.convertToDto(user.get());
            return ResponseEntity.ok(userDTO);
        }else {
            return ResponseEntity.status(404).body(null);
        }
    }


    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest updateUser) {
        User updatedUser = userService.updateOneUser(userId, updateUser);
        if(updatedUser !=null){
            return ResponseEntity.ok(updatedUser);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteOneUser(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }
    }

}