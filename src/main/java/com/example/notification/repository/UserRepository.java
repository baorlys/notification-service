package com.example.notification.repository;

import com.example.notification.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM user_info u WHERE u.email = ?1")
    User findByEmail(String email);
}
