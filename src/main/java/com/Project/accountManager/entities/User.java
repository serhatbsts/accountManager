package com.Project.accountManager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "Please enter a valid email address")
    @NotEmpty(message = "Email field cannot be empty")
    @Column(unique = true)
    private String email;
    private String name;
    private String surName;
    private int password;
}