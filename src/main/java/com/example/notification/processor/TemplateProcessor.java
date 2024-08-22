package com.example.notification.processor;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public interface TemplateProcessor {
    String processTemplate(Map<String, Object> data, String template) throws TemplateException, IOException;
}
