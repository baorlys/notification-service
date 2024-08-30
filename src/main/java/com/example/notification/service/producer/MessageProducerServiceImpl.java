package com.example.notification.service.producer;

import com.example.notification.config.MessageConstants;
import com.example.notification.config.context.NotificationRouterContext;
import com.example.notification.config.context.RequestContext;
import com.example.notification.enums.TargetOutput;
import com.example.notification.input.Content;
import com.example.notification.input.NotificationRequest;
import com.example.notification.model.TemplateMessage;
import com.example.notification.processor.TemplateProcessor;
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
public class MessageProducerServiceImpl implements MessageProducerService {
    AmqpTemplate amqpTemplate;
    TemplateProcessor templateProcessor;
    TemplateService templateService;
    ObjectMapper objectMapper;

    @Override
    public void publishMessage(NotificationRequest req) throws IOException, TemplateException {
        TemplateMessage templateMsg = Optional.ofNullable(req.getTemplateId())
                .map(templateService::getTemplateById)
                .orElse(null);
        NotificationRouterContext.set(req.getTargetOutput(), req.getFrom(), req.getSubject(), templateMsg);

        sendMultiMessage(req);

        RequestContext.clear();
        NotificationRouterContext.clear();
    }

    public void sendMultiMessage(NotificationRequest req) throws IOException, TemplateException {
        List<Map.Entry<String, String>> dataRecs = mergeDataWithTemplate(NotificationRouterContext.getTemplate(),
                req.getTos(),
                req.getContents());
        for (Map.Entry<String, String> data : dataRecs) {
            sendMessage(data.getKey(), data.getValue());
        }
    }

    private List<Map.Entry<String, String>> mergeDataWithTemplate(TemplateMessage templateMsg,
                                                                  List<String> tos,
                                                                  List<Content> bodies)
            throws TemplateException, IOException {
        List<Map.Entry<String, String>> messages = new ArrayList<>();

        for (int i = 0; i < tos.size(); i++) {
            String to = tos.get(i);
            Content body = bodies.get(i);
            String genBody = templateProcessor.processTemplate(body.getValue(), templateMsg);
            messages.add(new AbstractMap.SimpleEntry<>(to, genBody));
        }

        return messages;
    }

    public void sendMessage(String to,
                            String body) throws JsonProcessingException {
        Map<String, Object> msg = new HashMap<>();
        msg.put(MessageConstants.FROM, NotificationRouterContext.getFrom());
        msg.put(MessageConstants.TO, to);
        msg.put(MessageConstants.SUBJECT, NotificationRouterContext.getSubject());
        msg.put(MessageConstants.BODY, body);

        String queueName = classifyMessage(NotificationRouterContext.getTargetOutput());

        amqpTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(msg));
    }

    private String classifyMessage(TargetOutput targetOutput) {
        return Optional.ofNullable(targetOutput.getQueueName())
                .orElseThrow(() -> new IllegalArgumentException("Queue name is null"));
    }


}
