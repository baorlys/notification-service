package com.example.notification.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class TemplateDTO {
    UUID id;
    String name;
    String content;
}
