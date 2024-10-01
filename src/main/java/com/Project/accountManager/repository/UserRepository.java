package com.Project.accountManager.repository;

import com.Project.accountManager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    User findByEmailAndPassword(String email, int password);
    boolean existsByEmail(String email);


}