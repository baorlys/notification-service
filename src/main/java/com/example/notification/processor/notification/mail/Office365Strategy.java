package com.example.notification.processor.notification.mail;

import com.example.notification.config.EnvironmentConfig;
import com.example.notification.input.SenderInfo;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.io.IOException;

// Did not test this class
public class Office365Strategy implements MailStrategy {
    EnvironmentConfig environmentConfig = new EnvironmentConfig();
    @Override
    public void sendEmail(SenderInfo from, String to, String subject, String body) throws IOException {
        Email email = EmailBuilder.startingBlank()
                .from(from.getName(), from.getContact())
                .to(to)
                .withSubject(subject)
                .withHTMLText(body)
                .withPlainText(body)
                .buildEmail();

        String username = environmentConfig.get("OFFICE365_USERNAME");
        String password = environmentConfig.get("OFFICE365_PASSWORD");

        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.office365.com", 587, username, password)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();

        mailer.sendMail(email);
    }
}
