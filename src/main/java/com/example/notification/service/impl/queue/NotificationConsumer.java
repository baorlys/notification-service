package com.example.notification.service.impl.queue;

import com.example.notification.input.EmailAddress;
import com.example.notification.processor.SendMailProcessor;
import com.example.notification.processor.SendSMSProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    SendMailProcessor sendMailProcessor;

    SendSMSProcessor sendSMSProcessor;

    ObjectMapper objectMapper = new ObjectMapper();



    @RabbitListener(queues = "sms-queue")
    public void receiveSmsNotification(String message) throws JsonProcessingException {
        Map<String,Object> msg = objectMapper.readValue(message, new TypeReference<>() {});
        sendSMSProcessor.process(
                objectMapper.convertValue(msg.get("from"), EmailAddress.class).getEmail(),
                (String) msg.get("to"),
                (String) msg.get("body")
        );
    }

    @RabbitListener(queues = "email-queue")
    public void receiveEmailNotification(String message) throws IOException {
        Map<String,Object> msg = objectMapper.readValue(message, new TypeReference<>() {});
        sendMailProcessor.process(
                objectMapper.convertValue(msg.get("from"), EmailAddress.class),
                (String) msg.get("to"),
                (String) msg.get("subject"),
                (String) msg.get("body")
        );
    }

}