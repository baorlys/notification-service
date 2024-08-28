package com.example.notification.processor;

import com.example.notification.input.SenderInfo;

import java.io.IOException;

public class MailProcessor extends ProcessorNotificationTemplate {
    MailStrategy mailStrategy;

    public MailProcessor(String message, MailStrategy mailStrategy) {
        super(message);
        this.mailStrategy = mailStrategy;
    }

    @Override
    void sendMessage(SenderInfo from, String to, String subject, String body) throws IOException {
        mailStrategy.sendEmail(from, to, subject, body);
    }



}
