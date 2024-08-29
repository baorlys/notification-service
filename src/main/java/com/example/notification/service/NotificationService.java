package com.example.notification.service;

import com.example.notification.input.Message;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface NotificationService {
    void processNotification(Message message) throws IOException, TemplateException;

}
