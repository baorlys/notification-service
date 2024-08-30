package com.example.notification.service;

import com.example.notification.input.NotificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ReceiverNotificationService {
    void receiveNotification(NotificationRequest message) throws JsonProcessingException;
}
