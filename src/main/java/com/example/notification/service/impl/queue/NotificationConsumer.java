package com.example.notification.service.impl.queue;

import com.example.notification.input.EmailAddress;
import com.example.notification.processor.SendMailProcessor;
import com.example.notification.processor.SendSmsProcessor;
import com.example.notification.processor.VoiceCallProcessor;
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

    SendSmsProcessor sendSMSProcessor;

    VoiceCallProcessor voiceCallProcessor;

    ObjectMapper objectMapper = new ObjectMapper();

    static final String EMAIL_QUEUE = "email-queue";
    static final String SMS_QUEUE = "sms-queue";
    static final String VOICE_QUEUE = "voice-queue";


    @RabbitListener(queues = SMS_QUEUE)
    public void receiveSmsNotification(String message) throws JsonProcessingException {
        Map<String,Object> msg = objectMapper.readValue(message, new TypeReference<>() {});
        sendSMSProcessor.process(
                objectMapper.convertValue(msg.get("from"), EmailAddress.class).getEmail(),
                (String) msg.get("to"),
                (String) msg.get("body")
        );
    }

    @RabbitListener(queues = EMAIL_QUEUE)
    public void receiveEmailNotification(String message) throws IOException {
        Map<String,Object> msg = objectMapper.readValue(message, new TypeReference<>() {});
        sendMailProcessor.process(
                objectMapper.convertValue(msg.get("from"), EmailAddress.class),
                (String) msg.get("to"),
                (String) msg.get("subject"),
                (String) msg.get("body")
        );
    }

    @RabbitListener(queues = VOICE_QUEUE)
    public void receiveVoiceNotification(String message) throws IOException {
        Map<String,Object> msg = objectMapper.readValue(message, new TypeReference<>() {});
        voiceCallProcessor.process(
                (String) msg.get("to"),
                (String) msg.get("body")
        );
    }

}