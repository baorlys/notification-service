package com.example.notification.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dynamic")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicConfig {
    String strategyMail;
}
