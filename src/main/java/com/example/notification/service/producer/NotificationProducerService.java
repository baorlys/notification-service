package com.example.notification.service.producer;

import com.example.notification.input.NotificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface NotificationProducerService {
    void publishNotification(NotificationRequest message) throws JsonProcessingException;
}
