package com.example.notification.processor.notification;

import com.example.notification.input.SenderInfo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsProcessor extends ProcessorNotificationTemplate {

    public SmsProcessor(String message) {
        super(message);
    }

    @Override
    void sendMessage(SenderInfo from, String to, String subject, String body) {
        var accountSid = environmentConfig.get("TWILIO_ACCOUNT_SID");
        var authToken = environmentConfig.get("TWILIO_AUTH_TOKEN");

        Twilio.init(accountSid, authToken);

        Message.creator(
                new com.twilio.type.PhoneNumber(to),
                new com.twilio.type.PhoneNumber(from.getContact()),
                body
        ).create();
    }
}
