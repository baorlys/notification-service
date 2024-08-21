package com.example.notification.repository;

import com.example.notification.model.ScheduleNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleNotificationRepository extends JpaRepository<ScheduleNotification, UUID> {
}
