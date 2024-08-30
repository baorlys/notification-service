package com.example.notification.service.producer;

import com.example.notification.input.NotificationRequest;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface MessageProducerService {
    void publishMessage(NotificationRequest message) throws IOException, TemplateException;

}
