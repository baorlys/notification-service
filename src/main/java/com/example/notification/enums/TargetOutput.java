package com.example.notification.enums;

public enum TargetOutput {
    EMAIL("email-queue"),
    SMS("sms-queue"),
    PUSH("push-queue"),
    VOICE("voice-queue"),
    MEDIA("media-queue"),
    SOCIAL("social-queue");

    private final String queueName;

    TargetOutput(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

}
