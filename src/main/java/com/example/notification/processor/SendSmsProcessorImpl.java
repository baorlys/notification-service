package com.example.notification.processor;

import com.example.notification.config.EnvironmentConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendSmsProcessorImpl implements SendSmsProcessor {
    EnvironmentConfig environmentConfig;
    @Override
    public void process(String from, String to, String body) {
        var accountSid = environmentConfig.get("TWILIO_ACCOUNT_SID");
        var authToken = environmentConfig.get("TWILIO_AUTH_TOKEN");

        Twilio.init(accountSid, authToken);

        Message.creator(
                new com.twilio.type.PhoneNumber(to),
                new com.twilio.type.PhoneNumber(from),
                body
        ).create();
    }
}
