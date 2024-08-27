package com.example.notification.processor;

public interface SendSmsProcessor {
    void process(String from, String to, String body);
}
