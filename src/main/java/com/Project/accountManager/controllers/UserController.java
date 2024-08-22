package com.Project.accountManager.controllers;

import com.Project.accountManager.dto.userRequest.CreateUserRequest;
import com.Project.accountManager.dto.userRequest.LoginUserRequest;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> login(@RequestBody LoginUserRequest loginRequest){
        User loggedInUser=userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (loggedInUser!=null){
            return ResponseEntity.ok("Başarılı Giriş");
        }else {
            return ResponseEntity.status(401).body("Kullanıcı adı veya şifre yanlış!");
        }
    }


    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
        return userService.updateOneUser(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteOneUser(userId);
    }
}