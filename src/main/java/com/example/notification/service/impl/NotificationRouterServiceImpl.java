package com.example.notification.service.impl;

import com.example.notification.config.MessageConstants;
import com.example.notification.config.RequestContext;
import com.example.notification.enums.TargetOutput;
import com.example.notification.input.Content;
import com.example.notification.input.NotificationRequest;
import com.example.notification.model.Notification;
import com.example.notification.model.Recipient;
import com.example.notification.model.Template;
import com.example.notification.model.User;
import com.example.notification.processor.TemplateProcessor;
import com.example.notification.repository.NotificationRepository;
import com.example.notification.repository.RecipientRepository;
import com.example.notification.service.NotificationRouterService;
import com.example.notification.service.TemplateService;
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
public class NotificationRouterServiceImpl implements NotificationRouterService {
    AmqpTemplate amqpTemplate;
    TemplateProcessor templateProcessor;
    NotificationRepository notificationRepository;
    RecipientRepository recipientRepository;
    TemplateService templateService;
    ObjectMapper objectMapper;

    @Override
    public void sendMessageToQueue(NotificationRequest req) throws IOException, TemplateException {
        Notification notification = createNotification(req, new User(req.getFrom().getUserId()));
        saveRecipients(notification, req.getTos(), req.getContents());
        sendMultiMessage(notification, req);
        RequestContext.clear();
    }


    private Notification createNotification(NotificationRequest req, User user) {
        Notification notification = new Notification();
        notification.setUser(user);

        Template template = Optional.ofNullable(req.getTemplateId())
                .map(templateService::getTemplateById)
                .orElse(null);
        notification.setTemplate(template);

        notification.setSender(req.getFrom().getContact());
        notification.setTargetOutput(req.getTargetOutput());
        return notificationRepository.save(notification);
    }

    private void saveRecipients(Notification notif,
                                List<String> tos,
                                List<Content> bodies)
            throws JsonProcessingException {
        int size = Math.min(tos.size(), bodies.size());
        List<Recipient> recipients = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Recipient rec = new Recipient();
            rec.setNotification(notif);
            rec.setContact(tos.get(i));

            // If there is only one body, use it for all recipients
            Content contentToSerialize = bodies.size() == 1 ? bodies.get(0) : bodies.get(i);
            rec.setData(objectMapper.writeValueAsString(contentToSerialize.getValue()));

            recipients.add(rec);
        }

        recipientRepository.saveAll(recipients);
    }

    private void sendMultiMessage(Notification notif,
                                  NotificationRequest req) throws IOException, TemplateException {
        String template = Optional.ofNullable(notif.getTemplate())
                .map(Template::getBody)
                .orElse(null);
        List<Map.Entry<String, String>> notifications = mergeDataWithTemplate(template, req.getTos(), req.getContents());
        for (Map.Entry<String, String> entry : notifications) {
            sendMessage(req, entry.getKey(), entry.getValue());
        }
    }

    private void sendMessage(NotificationRequest req,
                             String to,
                             String body) throws JsonProcessingException {
        Map<String, Object> msg = prepareMessage(req, to, body);
        String queueName = classifyMessage(req.getTargetOutput());
        amqpTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(msg));
    }

    private Map<String, Object> prepareMessage(NotificationRequest req,
                                               String to,
                                               String body) {
        Map<String, Object> notification = new HashMap<>();
        notification.put(MessageConstants.FROM, req.getFrom());
        notification.put(MessageConstants.TO, to);
        notification.put(MessageConstants.SUBJECT, req.getSubject());
        notification.put(MessageConstants.BODY, body);
        return notification;
    }

    private String classifyMessage(TargetOutput targetOutput) {
        return Optional.ofNullable(targetOutput.getQueueName())
                .orElseThrow(() -> new IllegalArgumentException("Queue name is null"));
    }

    private List<Map.Entry<String, String>> mergeDataWithTemplate(String template,
                                                                  List<String> tos,
                                                                  List<Content> bodies)
            throws TemplateException, IOException {
        List<Map.Entry<String, String>> notifies = new ArrayList<>();

        for (int i = 0; i < tos.size(); i++) {
            String to = tos.get(i);
            Content body = bodies.get(i);
            String genBody = createBody(template, body);
            notifies.add(new AbstractMap.SimpleEntry<>(to, genBody));
        }

        return notifies;
    }

    private String createBody(String template,
                              Content content) throws TemplateException, IOException {
        if (template == null) {
            return String.valueOf(content.getValue());
        }
        return templateProcessor.processTemplate(content.getValue(), template);
    }


}
