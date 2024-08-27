package com.example.notification.processor;

import com.example.notification.config.EnvironmentConfig;
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
        SenderInfo from = objectMapper.convertValue(messageMap.get("from"), SenderInfo.class);
        String to = (String) messageMap.get("to");
        String subject = (String) messageMap.get("subject");
        String body = (String) messageMap.get("body");
        processStrategy(from,to,subject,body);
    }

    abstract void processStrategy(SenderInfo from, String to, String subject, String body) throws IOException;

    private Map<String,Object> convertMessageToMap() throws JsonProcessingException {
        return objectMapper.readValue(message, new TypeReference<>() {});
    }
}
