package com.example.notification.service.impl.queue;

import com.example.notification.config.QueueConstants;
import com.example.notification.config.StringeeAPIConfig;
import com.example.notification.processor.ProcessorNotificationTemplate;
import com.example.notification.processor.SendMailStrategy;
import com.example.notification.processor.SendSmsStrategy;
import com.example.notification.processor.VoiceCallStrategy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {
    StringeeAPIConfig stringeeAPIConfig;


    @RabbitListener(queues = QueueConstants.SMS_QUEUE)
    public void receiveSmsNotification(String message) throws IOException {
        ProcessorNotificationTemplate processor = new SendSmsStrategy(message);
        processor.process();
    }

    @RabbitListener(queues = QueueConstants.EMAIL_QUEUE)
    public void receiveEmailNotification(String message) throws IOException {
        ProcessorNotificationTemplate processor = new SendMailStrategy(message);
        processor.process();
    }

    @RabbitListener(queues = QueueConstants.VOICE_QUEUE)
    public void receiveVoiceNotification(String message) throws IOException {
        ProcessorNotificationTemplate processor = new VoiceCallStrategy(
                message,
                stringeeAPIConfig);
        processor.process();
    }

}