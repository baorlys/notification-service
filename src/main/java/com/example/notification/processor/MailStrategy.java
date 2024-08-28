package com.example.notification.processor;

import com.example.notification.input.SenderInfo;

import java.io.IOException;

public interface MailStrategy {
    void sendEmail(SenderInfo from, String to, String subject, String body) throws IOException;
}
