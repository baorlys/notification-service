package com.example.notification.global.factory;

import com.example.notification.input.TemplateInput;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ContentFactory {
    public String extractContent(TemplateInput input) throws IOException {
        switch (input.getType()) {
            case HTML:
                return readFileContent(input.getFile());
            case PLAIN_TEXT:
                return input.getContent();
            case MARKDOWN:
                return "";
            default:
                throw new IllegalArgumentException("Unsupported template type: " + input.getType());
        }
    }

    private String readFileContent(MultipartFile file) throws IOException {
        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }
}
