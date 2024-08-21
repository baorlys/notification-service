package com.example.notification.repository;

import com.example.notification.model.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WebhookRepository extends JpaRepository<Webhook, UUID> {
}
