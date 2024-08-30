package com.example.notification.service.producer;

import com.example.notification.config.QueueConstants;
import com.example.notification.config.context.RequestContext;
import com.example.notification.dto.CredentialDTO;
import com.example.notification.global.service.CommonService;
import com.example.notification.input.Content;
import com.example.notification.input.NotificationRequest;
import com.example.notification.service.CredentialService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationProducerServiceImpl implements NotificationProducerService {
    static final int MAX_MESSAGE_SIZE = 134217728; // 128 MB
    AmqpTemplate amqpTemplate;
    CredentialService credentialService;
    ObjectMapper objectMapper;

    @Override
    public void publishNotification(NotificationRequest message) throws JsonProcessingException {
        String jsonMessage = validateMessage(message);
        amqpTemplate.convertAndSend(QueueConstants.NOTIFICATION_QUEUE, jsonMessage);
    }

    private int getLength(String message) {
        byte[] messageBodyBytes = message.getBytes();
        return messageBodyBytes.length;
    }

    private String validateMessage(NotificationRequest message) throws JsonProcessingException {
        CommonService.throwIsNull(message, "Message is null");
        validateRecipientAndContent(message.getTos(), message.getContents());

        message.getFrom().setUserId(getUserIdByCurrentCredential());

        String jsonMessage = objectMapper.writeValueAsString(message);
        validateSize(jsonMessage);

        return jsonMessage;
    }

    private UUID getUserIdByCurrentCredential() {
        CredentialDTO credentialDTO = new CredentialDTO(RequestContext.getApiKey(), RequestContext.getSecretKey());
        return credentialService.getUserIdByCredential(credentialDTO);
    }

    private void validateSize(String message) {
        CommonService.throwIsOverSizeMessage(getLength(message), MAX_MESSAGE_SIZE, "Message size is over 128 MB");
    }

    private void validateRecipientAndContent(List<String> tos, List<Content> contents) {
        CommonService.throwIsNull(tos, "Recipients is null");
        CommonService.throwIsNull(contents, "Contents is null");
        CommonService.throwArgumentException(contents.size() != tos.size() && contents.size() != 1, "Contents size is not equal to Recipients size");
    }


}
