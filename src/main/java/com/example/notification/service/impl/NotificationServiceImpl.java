package com.example.notification.service.impl;

import com.example.notification.enums.TargetOutput;
import com.example.notification.input.Content;
import com.example.notification.input.SendMessage;
import com.example.notification.model.Notification;
import com.example.notification.model.Recipient;
import com.example.notification.model.Template;
import com.example.notification.model.User;
import com.example.notification.processor.TemplateProcessor;
import com.example.notification.repository.NotificationRepository;
import com.example.notification.repository.RecipientRepository;
import com.example.notification.repository.TemplateRepository;
import com.example.notification.repository.UserRepository;
import com.example.notification.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    AmqpTemplate amqpTemplate;
    TemplateProcessor templateProcessor;
    NotificationRepository notificationRepository;
    RecipientRepository recipientRepository;
    UserRepository userRepository;
    TemplateRepository templateRepository;
    ObjectMapper objectMapper;

    @Override
    public void processNotification(SendMessage msg) throws IOException, TemplateException {
        User user = fetchUser(msg.getFrom().getEmail());
        Notification notify = createNotification(msg, user);
        saveRecipients(notify, msg.getTos(), msg.getContents());
        sendNotifications(notify, msg);
    }

    private User fetchUser(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private Notification createNotification(SendMessage msg, User user) {
        Template template = fetchTemplate(msg.getTemplateId());
        Notification notify = new Notification();
        notify.setUser(user);
        notify.setTemplate(template);
        notify.setSender(msg.getFrom().getEmail());
        notify.setTargetOutput(msg.getTargetOutput());
        return notificationRepository.save(notify);
    }

    private Template fetchTemplate(String templateId) {
        return Optional.ofNullable(templateId)
                .map(UUID::fromString)
                .flatMap(templateRepository::findById)
                .orElse(null);
    }

    private void saveRecipients(Notification notification, List<String> tos, List<Content> bodies)
            throws JsonProcessingException {
        List<Recipient> recs = createRecipients(notification, tos, bodies);
        recipientRepository.saveAll(recs);
    }

    private List<Recipient> createRecipients(Notification notification, List<String> tos, List<Content> bodies)
            throws JsonProcessingException {
        int size = Math.min(tos.size(), bodies.size());
        List<Recipient> recipients = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            recipients.add(createRecipient(notification, tos.get(i), bodies.get(i)));
        }
        return recipients;
    }

    private Recipient createRecipient(Notification notification, String contact, Content content)
            throws JsonProcessingException {
        Recipient rec = new Recipient();
        rec.setNotification(notification);
        rec.setContact(contact);
        rec.setData(objectMapper.writeValueAsString(content.getValue()));
        return rec;

    }

    private void sendNotifications(Notification notification, SendMessage msg) throws IOException, TemplateException {
        List<Map.Entry<String, String>> notifications = prepareNotifications(notification, msg.getTos(), msg.getContents());
        for (Map.Entry<String, String> entry : notifications) {
            sendNotification(msg, entry.getKey(), entry.getValue());
        }
    }

    private List<Map.Entry<String, String>> prepareNotifications(Notification notification, List<String> tos, List<Content> bodies)
            throws TemplateException, IOException {
        Template template = notification.getTemplate();
        List<Map.Entry<String, String>> notifies = new ArrayList<>();

        for (int i = 0; i < tos.size(); i++) {
            String to = tos.get(i);
            Content body = bodies.get(i);
            String notifyBody = createNotificationBody(template, body);
            notifies.add(new AbstractMap.SimpleEntry<>(to, notifyBody));
        }

        return notifies;
    }

    private String createNotificationBody(Template template, Content content) throws TemplateException, IOException {
        if (template == null) {
            return String.valueOf(content.getValue());
        }
        return generateBody(template.getBody(), content);
    }

    private void sendNotification(SendMessage msg, String to, String body) throws JsonProcessingException {
        Map<String, Object> notify = createPublicMessage(msg, to, body);
        publishNotification(msg.getTargetOutput(), notify);
    }

    private Map<String, Object> createPublicMessage(SendMessage msg, String to, String body) {
        Map<String, Object> notify = new HashMap<>();
        notify.put("from", msg.getFrom());
        notify.put("to", to);
        notify.put("subject", msg.getSubject());
        notify.put("body", body);
        return notify;
    }

    private void publishNotification(TargetOutput targetOutput, Object msg) throws JsonProcessingException {
        String queueName = Optional.ofNullable(targetOutput.getQueueName())
                .orElseThrow(() -> new IllegalArgumentException("Queue name is null"));
        amqpTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(msg));
    }

    private String generateBody(String template, Content content) throws TemplateException, IOException {
        return templateProcessor.processTemplate(content.getValue(), template);
    }
}
