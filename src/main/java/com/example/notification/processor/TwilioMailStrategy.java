package com.example.notification.processor;

import com.example.notification.config.EnvironmentConfig;
import com.example.notification.input.SenderInfo;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class TwilioMailStrategy implements MailStrategy {
    EnvironmentConfig environmentConfig = new EnvironmentConfig();
    @Override
    public void sendEmail(SenderInfo from, String to, String subject, String body) throws IOException {
        Email fromEmail = new Email(environmentConfig.get("SENDGRID_EMAIL"));
        Email toEmail = new Email(to);
        Content content = new Content("text/html", body);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        SendGrid sg = new SendGrid(environmentConfig.get("SENDGRID_API_KEY"));

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }
}
