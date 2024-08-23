package com.example.notification.service.impl.queue;

import com.example.notification.config.EnvironmentConfig;
import com.example.notification.input.EmailAddress;
import com.example.notification.processor.SendMailProcessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {
    EnvironmentConfig environmentConfig;

    SendMailProcessor sendMailProcessor;
    ObjectMapper objectMapper = new ObjectMapper();



    @RabbitListener(queues = "smsQueue")
    public void receiveSmsNotification(String message) {
        // Process SMS notification
        System.out.println("Received SMS: " + message);
    }

    @RabbitListener(queues = "emailQueue")
    public void receiveEmailNotification(String message) throws IOException {
        Map<String,Object> msg = objectMapper.readValue(message, new TypeReference<>() {});
        sendMailProcessor.process(
                objectMapper.convertValue(msg.get("from"), EmailAddress.class),
                (String) msg.get("to"),
                (String) msg.get("subject"),
                (String) msg.get("body")
        );
    }

    @RabbitListener(queues = "pushQueue")
    public void receivePushNotification(String message) {
        // Process Push notification
        System.out.println("Received Push: " + message);
    }
}