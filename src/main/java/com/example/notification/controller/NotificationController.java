package com.example.notification.controller;

import com.example.notification.input.MessageRequest;
import com.example.notification.service.NotificationService;
import freemarker.template.TemplateException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/notification")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class NotificationController {
    NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@Valid @RequestBody MessageRequest request) throws IOException, TemplateException {
        notificationService.processNotification(request);
        return ResponseEntity.ok().build();
    }

}
