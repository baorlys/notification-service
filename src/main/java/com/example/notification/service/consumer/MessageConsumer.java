package com.example.notification.service.consumer;

import com.example.notification.config.DynamicConfig;
import com.example.notification.config.QueueConstants;
import com.example.notification.config.StringeeAPIConfig;
import com.example.notification.processor.message.MessageProcessorTemplate;
import com.example.notification.processor.message.SmsMessageProcessor;
import com.example.notification.processor.message.VoiceCallMessageProcessor;
import com.example.notification.processor.message.mail.MailFactory;
import com.example.notification.processor.message.mail.MailMessageProcessor;
import com.example.notification.processor.message.mail.MailStrategy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageConsumer {
    StringeeAPIConfig stringeeAPIConfig;
    DynamicConfig dynamicConfig;

    @RabbitListener(queues = QueueConstants.SMS_QUEUE)
    public void receiveSmsNotification(String message) throws IOException {
        MessageProcessorTemplate processor = new SmsMessageProcessor(message);
        processor.process();
    }

    @RabbitListener(queues = QueueConstants.EMAIL_QUEUE)
    public void receiveEmailNotification(String message) throws IOException {
        MailStrategy strategy = MailFactory.getMailStrategy(dynamicConfig.getStrategyMail());
        MessageProcessorTemplate processor = new MailMessageProcessor(message, strategy);
        processor.process();
    }

    @RabbitListener(queues = QueueConstants.VOICE_QUEUE)
    public void receiveVoiceNotification(String message) throws IOException {
        MessageProcessorTemplate processor = new VoiceCallMessageProcessor(
                message,
                stringeeAPIConfig);
        processor.process();
    }

}