package com.example.notification.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestContext {
    private static final ThreadLocal<String> apiKeyHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> secretKeyHolder = new ThreadLocal<>();

    private RequestContext() {
        // static class
    }

    public static String getApiKey() {
        return apiKeyHolder.get();
    }

    public static void setApiKey(String apiKey) {
        apiKeyHolder.set(apiKey);
    }

    public static String getSecretKey() {
        return secretKeyHolder.get();
    }

    public static void setSecretKey(String secretKey) {
        secretKeyHolder.set(secretKey);
    }

    public static void clear() {
        apiKeyHolder.remove();
        secretKeyHolder.remove();
    }
}

