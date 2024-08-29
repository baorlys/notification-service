package com.example.notification.service.queue;

import com.example.notification.config.DynamicConfig;
import com.example.notification.config.QueueConstants;
import com.example.notification.config.StringeeAPIConfig;
import com.example.notification.processor.notification.ProcessorNotificationTemplate;
import com.example.notification.processor.notification.SmsProcessor;
import com.example.notification.processor.notification.VoiceCallProcessor;
import com.example.notification.processor.notification.mail.MailFactory;
import com.example.notification.processor.notification.mail.MailProcessor;
import com.example.notification.processor.notification.mail.MailStrategy;
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
    DynamicConfig dynamicConfig;

    @RabbitListener(queues = QueueConstants.SMS_QUEUE)
    public void receiveSmsNotification(String message) throws IOException {
        ProcessorNotificationTemplate processor = new SmsProcessor(message);
        processor.process();
    }

    @RabbitListener(queues = QueueConstants.EMAIL_QUEUE)
    public void receiveEmailNotification(String message) throws IOException {
        MailStrategy strategy = MailFactory.getMailStrategy(dynamicConfig.getStrategyMail());
        ProcessorNotificationTemplate processor = new MailProcessor(message, strategy);
        processor.process();
    }

    @RabbitListener(queues = QueueConstants.VOICE_QUEUE)
    public void receiveVoiceNotification(String message) throws IOException {
        ProcessorNotificationTemplate processor = new VoiceCallProcessor(
                message,
                stringeeAPIConfig);
        processor.process();
    }

}