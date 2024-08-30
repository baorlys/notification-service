package com.example.notification.processor.message.mail;

import com.example.notification.input.SenderInfo;
import com.example.notification.processor.message.MessageProcessorTemplate;

import java.io.IOException;

public class MailMessageProcessor extends MessageProcessorTemplate {
    MailStrategy mailStrategy;

    public MailMessageProcessor(String message, MailStrategy mailStrategy) {
        super(message);
        this.mailStrategy = mailStrategy;
    }

    @Override
    protected void sendMessage(SenderInfo from, String to, String subject, String body) throws IOException {
        mailStrategy.sendEmail(from, to, subject, body);
    }


}
