package com.example.notification.processor;

import java.io.IOException;

public interface VoiceCallProcessor {
    void process(String to, String message) throws IOException;
}
