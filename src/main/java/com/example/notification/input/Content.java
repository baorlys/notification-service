package com.example.notification.input;

import com.example.notification.enums.TemplateType;
import lombok.Data;

@Data
public class Content {
    TemplateType type;
    Object value;
}
