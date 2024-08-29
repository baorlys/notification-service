package com.example.notification.input;

import com.example.notification.enums.TargetOutput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class MessageRequest {

    @NotNull(message = "Target output is required")
    @Valid
    TargetOutput targetOutput;

    @NotNull(message = "Sender information is required")
    @Valid
    SenderInfo from;

    @NotEmpty(message = "At least one recipient is required")
    @Size(max = 100, message = "Cannot send to more than 100 recipients")
    List<@Pattern(
            regexp = "^(\\+?[0-9. ()-]{7,25})|([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})$",
            message = "Each recipient in 'tos' must be a valid phone number or email address"
    ) String> tos;


    String subject;

    String templateId;

    @NotEmpty(message = "Contents cannot be empty")
    @Valid
    List<Content> contents;

}
