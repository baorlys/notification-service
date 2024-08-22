package com.example.notification.input;

import com.example.notification.enums.TargetOutput;
import lombok.Data;

import java.util.List;

@Data
public class SendMessage {
    TargetOutput targetOutput;
    EmailAddress emailAddress;
    List<String> tos;
    String subject;
    String templateId;
    List<Content> contents;

}
