package com.example.notification.repository;

import com.example.notification.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipientRepository extends JpaRepository<Recipient, UUID> {
}
