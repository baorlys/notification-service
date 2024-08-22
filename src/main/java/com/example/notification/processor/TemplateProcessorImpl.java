package com.example.notification.processor;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Component
public class TemplateProcessorImpl implements TemplateProcessor {
    private final Configuration configuration;

    public TemplateProcessorImpl() {
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDefaultEncoding("UTF-8");
    }

    public String processTemplate(Map<String, Object> data, String template) throws TemplateException, IOException {
        Template temp = new Template("", template, configuration);
        StringWriter writer = new StringWriter();
        temp.process(data, writer);
        return writer.toString();
    }
}
