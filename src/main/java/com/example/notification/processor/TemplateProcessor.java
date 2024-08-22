package com.example.notification.processor;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface TemplateProcessor {
    String processTemplate(Object data, String template) throws TemplateException, IOException;
}
