package com.example.notification;

import com.example.notification.config.EnvironmentConfig;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestSendMail {

    @Mock
    private Dotenv dotenv;

    @InjectMocks
    private EnvironmentConfig environmentConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMailWithSendGrid() throws IOException {
        Email from = new Email("nguyenvoankhoa@gmail.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("baorlys.dev@gmail.com");
        Content content = new Content("text/plain", "hello {name}!");
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(environmentConfig.get("SENDGRID_API_KEY"));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        assertEquals(202, response.getStatusCode());
    }


    @Test
    void testSendMailWithJavaSimpleMail() {
        org.simplejavamail.api.email.Email email = EmailBuilder.startingBlank()
                .from("notification service","baorlys.dev@gmail.com")
                .to("lygiabaokg2002@gmail.com")
                .withSubject("Email with Plain Text!")
                .withPlainText("This is a test email sent using SJM.")
                .buildEmail();
        var username = environmentConfig.get("GMAIL_USERNAME");
        var password = environmentConfig.get("GMAIL_PASSWORD");
        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 25, username, password)
                .buildMailer();

        mailer.sendMail(email);
    }
}
