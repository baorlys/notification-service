package com.example.notification.processor;

import com.example.notification.model.TemplateMessage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;

@Component
public class TemplateProcessorImpl implements TemplateProcessor {
    private final Configuration configuration;

    public TemplateProcessorImpl() {
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDefaultEncoding("UTF-8");
    }

    public String processTemplate(Object data, TemplateMessage template) throws TemplateException, IOException {
        if (template == null) {
            return data.toString();
        }

        Template temp = new Template("", template.getBody(), configuration);
        StringWriter writer = new StringWriter();
        temp.process(data, writer);
        return writer.toString();
    }
}
