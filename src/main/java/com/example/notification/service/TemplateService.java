package com.example.notification.service;

import com.example.notification.input.TemplateInput;
import com.example.notification.model.TemplateMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface TemplateService {
    TemplateMessage createTemplate(UUID userId, TemplateInput input);

    String processTemplate(Map<String, Object> data, TemplateMessage templateMsg);

    TemplateMessage getTemplateById(UUID id);

    List<TemplateMessage> getUserTemplates(UUID userId, int pageNum, int pageSize, String sortBy);

    TemplateMessage updateTemplate(UUID id, TemplateInput input);
}
