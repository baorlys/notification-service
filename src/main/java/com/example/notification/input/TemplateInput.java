package com.example.notification.input;

import com.example.notification.enums.TemplateType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Data
public class TemplateInput {
    String name;
    MultipartFile file;
    String content;
    TemplateType type;
    LocalDateTime updateAt = LocalDateTime.now();
}
