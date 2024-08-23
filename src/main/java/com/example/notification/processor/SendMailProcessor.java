package com.example.notification.processor;

import com.example.notification.input.EmailAddress;

public interface SendMailProcessor {
    void process(EmailAddress from, String to, String subject, String body);

}
