package com.example.notification.service.queue;

import com.example.notification.config.QueueConstants;
import com.example.notification.input.NotificationRequest;
import com.example.notification.service.NotificationRouterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {
    NotificationRouterService notificationRouterService;
    ObjectMapper objectMapper;

    @RabbitListener(queues = QueueConstants.NOTIFICATION_QUEUE)
    public void transferMessageToQueue(String message) throws IOException, TemplateException {
        NotificationRequest notificationRequest = objectMapper.readValue(message, NotificationRequest.class);
        notificationRouterService.sendMessageToQueue(notificationRequest);
    }
}
