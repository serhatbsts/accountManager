package com.Project.accountManager.services;

import com.Project.accountManager.dto.UserDTO;
import com.Project.accountManager.dto.userRequest.CreateUserRequest;
import com.Project.accountManager.dto.userRequest.UpdateUserRequest;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO convertToDto(User user){
        UserDTO userDTO=new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurName(user.getSurName());
        return userDTO;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        Assert.notNull(userId,"ID must not be null.");
        return userRepository.findById(userId);
    }
    public User login(String email,int password){
        User user=userRepository.findByEmailAndPassword(email,password);
        return user;
    }

    public User saveOneUser(CreateUserRequest newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())){
            throw new IllegalArgumentException("This email address is already registered.");
        }
        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setSurName(newUser.getSurName());
        user.setPassword(newUser.getPassword());
        return userRepository.save(user);
    }

    public boolean isEmailRegistered(String email){
        return userRepository.existsByEmail(email);
    }

    public User updateOneUser(Long userId, UpdateUserRequest updateUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setEmail(updateUser.getEmail());
            foundUser.setName(updateUser.getName());
            foundUser.setSurName(updateUser.getSurName());
          //  foundUser.setPassword(updateUser.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        } else {
            //custom exception add
            return null;
        }
    }

    public void deleteOneUser(Long userId) {
        userRepository.deleteById(userId);
    }
}