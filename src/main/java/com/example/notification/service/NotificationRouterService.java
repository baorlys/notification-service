package com.example.notification.service;

import com.example.notification.input.NotificationRequest;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface NotificationRouterService {
    void sendMessageToQueue(NotificationRequest message) throws IOException, TemplateException;

}
