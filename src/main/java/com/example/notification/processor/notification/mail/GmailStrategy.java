package com.example.notification.processor.notification.mail;

import com.example.notification.config.EnvironmentConfig;
import com.example.notification.input.SenderInfo;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class GmailStrategy implements MailStrategy {
    EnvironmentConfig environmentConfig = new EnvironmentConfig();
    @Override
    public void sendEmail(SenderInfo from, String to, String subject, String body) {
        Email email = EmailBuilder.startingBlank()
                .from(from.getName(), from.getContact())
                .to(to)
                .withSubject(subject)
                .withHTMLText(body)
                .withPlainText(body)
                .buildEmail();

        String username = environmentConfig.get("GMAIL_USERNAME");
        String password = environmentConfig.get("GMAIL_PASSWORD");

        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, username, password)
                .buildMailer();

        mailer.sendMail(email);
    }
}
