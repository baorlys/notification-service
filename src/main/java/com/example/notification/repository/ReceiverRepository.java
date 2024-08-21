package com.example.notification.repository;

import com.example.notification.model.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReceiverRepository extends JpaRepository<Receiver, UUID> {
}
