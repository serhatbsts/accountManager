package com.Project.accountManager.dto.userRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateUserRequest {
    @Email(message = "Please enter a valid email address")
    @NotEmpty(message = "Email field cannot be empty")
    private String email;
    private String name;
    private String surName;
    private int password;
}
