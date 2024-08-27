package com.example.notification.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stringee")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StringeeAPIConfig {
    String apiUrl;
    String answerUrl;
    String authHeader;
}
