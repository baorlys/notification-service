package com.example.notification.controller;

import com.example.notification.input.SendMessage;
import com.example.notification.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notification")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestBody SendMessage request) throws JsonProcessingException {
        notificationService.processNotification(request);
        return ResponseEntity.ok().build();
    }
}
