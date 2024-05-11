package com.Project.accountManager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="user")
@Data
public class User {

    @Id
    private Long id;
    String name;
    String surName;
    int password;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


}
