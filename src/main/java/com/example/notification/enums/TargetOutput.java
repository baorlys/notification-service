package com.example.notification.enums;

public enum TargetOutput {
    EMAIL("emailQueue"),
    SMS("smsQueue"),
    PUSH("pushQueue"),
    VOICE("voiceQueue"),
    MEDIA("mediaQueue"),
    SOCIAL("socialQueue");

    private final String queueName;

    TargetOutput(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

}
