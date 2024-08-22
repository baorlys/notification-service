package com.example.notification.input;

import com.example.notification.enums.TemplateType;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TemplateInput {
    String name;
    String content;
    TemplateType type;
    LocalDateTime updateAt = LocalDateTime.now();
}
