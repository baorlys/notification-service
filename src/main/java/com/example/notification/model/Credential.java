package com.example.notification.model;

import com.example.notification.enums.CredentialStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    User user;

    @Column(nullable = false)
    String apiKey;

    @Column(nullable = false)
    String secretKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CredentialStatus status;

    @Column(updatable = false, insertable = false)
    LocalDateTime createdAt = LocalDateTime.now();


}
