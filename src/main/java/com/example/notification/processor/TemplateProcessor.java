package com.example.notification.processor;

import com.example.notification.model.TemplateMessage;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface TemplateProcessor {
    String processTemplate(Object data, TemplateMessage templateMessage) throws TemplateException, IOException;
}
