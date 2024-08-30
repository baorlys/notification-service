package com.example.notification.service.consumer;

import com.example.notification.config.QueueConstants;
import com.example.notification.config.context.MessageTransferContext;
import com.example.notification.input.NotificationRequest;
import com.example.notification.service.TemplateService;
import com.example.notification.service.producer.MessageProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {
    MessageProducerService messageProducerService;
    TemplateService templateService;
    ObjectMapper objectMapper;

    @RabbitListener(queues = QueueConstants.NOTIFICATION_QUEUE)
    public void transferMessage(String message) throws IOException, TemplateException {
        NotificationRequest req = objectMapper.readValue(message, NotificationRequest.class);
        
        MessageTransferContext.set(req);
        MessageTransferContext.setTemplate(Optional.ofNullable(req.getTemplateId())
                .map(templateService::getTemplateById)
                .orElse(null));

        messageProducerService.publishMessage(req);
    }
}
