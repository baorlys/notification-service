package com.example.notification.service.impl;

import com.example.notification.config.context.NotificationRouterContext;
import com.example.notification.input.NotificationRequest;
import com.example.notification.model.Notification;
import com.example.notification.model.User;
import com.example.notification.repository.NotificationRepository;
import com.example.notification.service.NotificationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepository;

    @Override
    public void saveNotification(NotificationRequest req) {
        Notification notification = new Notification();
        notification.setUser(new User(NotificationRouterContext.getFrom().getUserId()));
        notification.setTemplateMessage(NotificationRouterContext.getTemplate());
        notification.setSender(req.getFrom().getContact());
        notification.setTargetOutput(req.getTargetOutput());
        notificationRepository.save(notification);
    }
}
