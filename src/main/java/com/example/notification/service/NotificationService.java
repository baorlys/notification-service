package com.example.notification.service;

import com.example.notification.input.MessageRequest;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface NotificationService {
    void processNotification(MessageRequest message) throws IOException, TemplateException;

}
