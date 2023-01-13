package com.example.finalproject.respository;

import com.example.finalproject.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// <User, Id>
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}


