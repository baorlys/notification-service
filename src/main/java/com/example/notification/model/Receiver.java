package com.example.notification.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Receiver {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    Notification notification;

    @Column(nullable = false)
    String contact;

    String data;

    boolean isRead = false;
}
