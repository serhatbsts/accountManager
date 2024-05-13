package com.Project.accountManager.controllers;

import com.Project.accountManager.entities.User;
import com.Project.accountManager.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userServices.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userServices.saveOneUser(newUser);
    }

    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId){
        return userServices.getOneUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId,@RequestBody User newUser){
        return userServices.updateOneUser(userId,newUser);
    }

    @DeleteMapping("/{userId}")
    public void  deleteOneUser(@PathVariable Long userId){
        userServices.deleteById(userId);
    }


}
