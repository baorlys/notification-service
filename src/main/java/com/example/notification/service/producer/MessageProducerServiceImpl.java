package com.example.notification.service.producer;

import com.example.notification.config.MessageConstants;
import com.example.notification.config.context.MessageTransferContext;
import com.example.notification.enums.TargetOutput;
import com.example.notification.input.Content;
import com.example.notification.input.NotificationRequest;
import com.example.notification.model.TemplateMessage;
import com.example.notification.processor.TemplateProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MessageProducerServiceImpl implements MessageProducerService {
    AmqpTemplate amqpTemplate;
    TemplateProcessor templateProcessor;
    ObjectMapper objectMapper;

    @Override
    public void publishMessage(NotificationRequest req) {
        sendMultiMessage(req);
        MessageTransferContext.clear();
    }

    public void sendMultiMessage(NotificationRequest req) {
        List<Map.Entry<String, String>> dataRecs = mergeDataWithTemplate(MessageTransferContext.getTemplate(),
                req.getTos(),
                req.getContents());
        dataRecs.forEach(data -> {
            try {
                sendMessage(data.getKey(), data.getValue());
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    private List<Map.Entry<String, String>> mergeDataWithTemplate(TemplateMessage templateMsg,
                                                                  List<String> tos,
                                                                  List<Content> bodies) {
        return tos.stream()
                .map(to -> {
                    Content body = bodies.size() > 1 ? bodies.get(tos.indexOf(to)) : bodies.get(0);
                    String genBody;
                    try {
                        genBody = templateProcessor.processTemplate(body.getValue(), templateMsg);
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                    return new AbstractMap.SimpleEntry<>(to, genBody);
                })
                .collect(Collectors.toList());
    }

    public void sendMessage(String to,
                            String body) throws JsonProcessingException {
        Map<String, Object> msg = new HashMap<>();
        msg.put(MessageConstants.FROM, MessageTransferContext.getFrom());
        msg.put(MessageConstants.TO, to);
        msg.put(MessageConstants.SUBJECT, MessageTransferContext.getSubject());
        msg.put(MessageConstants.BODY, body);

        String queueName = classifyMessage(MessageTransferContext.getTargetOutput());

        amqpTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(msg));
    }

    private String classifyMessage(TargetOutput targetOutput) {
        return Optional.ofNullable(targetOutput.getQueueName())
                .orElseThrow(() -> new IllegalArgumentException("Queue name is null"));
    }


}
