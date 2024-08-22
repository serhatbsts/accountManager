package com.Project.accountManager.repository;

import com.Project.accountManager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, int password);
    boolean existsByEmail(String email);

}