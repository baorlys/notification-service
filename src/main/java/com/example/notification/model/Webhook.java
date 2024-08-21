package com.example.notification.model;

import com.example.notification.enums.WebhookEvent;
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
public class Webhook {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    User user;

    String url;

    @Enumerated(EnumType.STRING)
    WebhookEvent event;

    @Column(updatable = false, insertable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt = LocalDateTime.now();
}
