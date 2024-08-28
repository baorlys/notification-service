package com.example.notification.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvironmentConfig {
    private final Dotenv dotenv;

    public EnvironmentConfig() {
        this.dotenv = Dotenv.load();
    }

    @Bean
    public Dotenv dotenv() {
        return this.dotenv;
    }

    public String get(String key) {
        return this.dotenv.get(key);
    }

}
