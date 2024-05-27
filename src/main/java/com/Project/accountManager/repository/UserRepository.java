package com.Project.accountManager.repository;

import com.Project.accountManager.entities.Account;
import com.Project.accountManager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
}