package com.example.notification.service;

import com.example.notification.input.SendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface NotificationService {
    void processNotification(SendMessage message) throws IOException, TemplateException;
}
