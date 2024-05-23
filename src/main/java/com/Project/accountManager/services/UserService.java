package com.Project.accountManager.services;

import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public User getOneUser(Long userId){
        return userRepository.findById(userId).orElse(null);
    }
    public User saveOneUser(User newUser){
        return userRepository.save(newUser);
    }
    public User updateOneUser(Long userId,User newUser){
        Optional<User> user=userRepository.findById(userId);
        if (user.isPresent()){
            User foundUser=user.get();
            foundUser.setName(newUser.getName());
            foundUser.setSurName(newUser.getSurName());
            foundUser.setPassword(newUser.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        }else {
            //custom exception add
            return null;
        }
    }

    public void deleteOneUser(Long userId){
        userRepository.deleteById(userId);
    }
}
