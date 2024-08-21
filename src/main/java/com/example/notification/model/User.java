package com.example.notification.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "user_info")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    UUID id;

    @Column(nullable = false)
    String name;

    @Column(unique = true, nullable = false)
    String email;

    @Column(unique = true)
    String phone;

    @Column(updatable = false, insertable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt = LocalDateTime.now();

}
