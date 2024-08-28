package com.example.notification.processor.notification.mail;

import com.example.notification.enums.TypeMailStrategy;

import java.util.EnumMap;
import java.util.Map;

public class MailFactory {
    private MailFactory() {
        // Factory class
    }
    private static final Map<TypeMailStrategy, MailStrategy> mailStrategyMap = new EnumMap<>(TypeMailStrategy.class);

    static {
        mailStrategyMap.put(TypeMailStrategy.GMAIL, new GmailStrategy());
        mailStrategyMap.put(TypeMailStrategy.TWILIO, new TwilioMailStrategy());
        mailStrategyMap.put(TypeMailStrategy.OFFICE365, new Office365Strategy());
    }

    public static MailStrategy getMailStrategy(TypeMailStrategy typeMailStrategy) {
        return mailStrategyMap.get(typeMailStrategy);
    }

    public static MailStrategy getMailStrategy(String typeMailStrategy) {
        return mailStrategyMap.get(TypeMailStrategy.valueOf(typeMailStrategy));
    }
}
