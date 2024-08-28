package com.example.notification.processor;

import com.example.notification.config.EnvironmentConfig;
import com.example.notification.config.MessageConstants;
import com.example.notification.input.SenderInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;


public abstract class ProcessorNotificationTemplate {
    String message;
    EnvironmentConfig environmentConfig = new EnvironmentConfig();
    ObjectMapper objectMapper = new ObjectMapper();

    protected ProcessorNotificationTemplate(String message) {
        this.message = message;
    }
    public void process() throws IOException {
        Map<String, Object> messageMap = convertMessageToMap();
        SenderInfo from = objectMapper.convertValue(messageMap.get(MessageConstants.FROM), SenderInfo.class);
        String to = (String) messageMap.get(MessageConstants.TO);
        String subject = (String) messageMap.get(MessageConstants.SUBJECT);
        String body = (String) messageMap.get(MessageConstants.BODY);
        sendMessage(from,to,subject,body);
    }

    abstract void sendMessage(SenderInfo from, String to, String subject, String body) throws IOException;

    private Map<String,Object> convertMessageToMap() throws JsonProcessingException {
        return objectMapper.readValue(message, new TypeReference<>() {});
    }
}
