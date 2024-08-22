package com.example.notification.service;

import com.example.notification.enums.TemplateType;
import com.example.notification.input.TemplateInput;
import com.example.notification.model.Template;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface TemplateService {
    Template createTemplate(UUID userId, String name, TemplateType type, String content);

    String processTemplate(Map<String, Object> data, String template);

    Template getTemplateById(UUID id);

    List<Template> getUserTemplates(UUID userId, int pageNum, int pageSize, String sortBy);

    Template updateTemplate(UUID id, TemplateInput input);
}
