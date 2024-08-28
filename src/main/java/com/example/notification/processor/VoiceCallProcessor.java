package com.example.notification.processor;

import com.example.notification.config.StringeeAPIConfig;
import com.example.notification.global.service.PhoneService;
import com.example.notification.input.SenderInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VoiceCallProcessor extends ProcessorNotificationTemplate {
    StringeeAPIConfig apiConfig;

    public VoiceCallProcessor(String message, StringeeAPIConfig apiConfig) {
        super(message);
        this.apiConfig = apiConfig;
    }

    @Override
    void sendMessage(SenderInfo from, String to, String subject, String body) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String data = buildJsonData(to, body);
        // Create request body
        RequestBody requestBody = RequestBody.create(data, MediaType.get("application/json; charset=utf-8"));
        // Build request
        Request request = new Request.Builder()
                .url(apiConfig.getApiUrl())
                .post(requestBody)
                .addHeader(apiConfig.getAuthHeader(), environmentConfig.get("STRINGEE_AUTH_TOKEN"))
                .build();
        client.newCall(request).execute();
    }


    private String buildJsonData(String toPhone, String message) throws JsonProcessingException {
        toPhone = PhoneService.prependCountryCode(toPhone);
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> from = new HashMap<>();
        from.put("type", "external");
        from.put("number", environmentConfig.get("STRINGEE_PHONE_NUMBER"));
        from.put("alias", environmentConfig.get("STRINGEE_PHONE_NUMBER"));
        data.put("from", from);

        Map<String, Object> to = new HashMap<>();
        to.put("type", "external");
        to.put("number", toPhone);
        to.put("alias", toPhone);
        data.put("to", List.of(to));

        data.put("answer_url", apiConfig.getAnswerUrl());

        Map<String, Object> action = new HashMap<>();
        action.put("action", "talk");
        action.put("text", message);
        data.put("actions", List.of(action));

        return objectMapper.writeValueAsString(data);
    }



}
