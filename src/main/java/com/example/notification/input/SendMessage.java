package com.example.notification.input;

import com.example.notification.enums.TargetOutput;
import lombok.Data;

import java.util.List;

@Data
public class SendMessage {
    TargetOutput targetOutput;
    EmailAddress from;
    List<String> tos;
    String subject;
    String templateId;
    List<Content> contents;

}
