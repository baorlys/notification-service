package com.example.notification.processor;

import com.example.notification.config.EnvironmentConfig;
import com.example.notification.input.EmailAddress;
import lombok.AllArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendMailProcessorImpl implements SendMailProcessor {
    EnvironmentConfig environmentConfig;

    @Override
    public void process(EmailAddress from, String to, String subject, String body) {
        Email email = EmailBuilder.startingBlank()
                .from(from.getName(), from.getEmail())
                .to(to)
                .withSubject(subject)
                .withHTMLText(body)
                .withPlainText(body)
                .buildEmail();
        sendWithConfig(email);
    }


    private void sendWithConfig(Email email) {
        var username = environmentConfig.get("GMAIL_USERNAME");
        var password = environmentConfig.get("GMAIL_PASSWORD");
        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 25, username, password)
                .buildMailer();

        mailer.sendMail(email);
    }


}
