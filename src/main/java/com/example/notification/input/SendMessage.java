package com.example.notification.input;

import com.example.notification.enums.TargetOutput;
import lombok.Data;

import java.util.List;

@Data
public class SendMessage {
    TargetOutput targetOutput;
    String from;
    List<String> to;
    String subject;
    Content content;

}
