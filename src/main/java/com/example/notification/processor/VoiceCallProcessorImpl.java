package com.example.notification.processor;

import com.example.notification.config.EnvironmentConfig;
import com.example.notification.config.StringeeAPIConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class VoiceCallProcessorImpl implements VoiceCallProcessor {
    EnvironmentConfig environmentConfig;
    StringeeAPIConfig apiConfig;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void process(String toPhone, String message) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String data = buildJsonData(toPhone, message);
        // Create request body
        RequestBody body = RequestBody.create(data, MediaType.get("application/json; charset=utf-8"));

        // Build request
        Request request = new Request.Builder()
                .url(apiConfig.getApiUrl())
                .post(body)
                .addHeader(apiConfig.getAuthHeader(), environmentConfig.get("STRINGEE_AUTH_TOKEN"))
                .build();
        client.newCall(request).execute();
    }


    private String buildJsonData(String toPhone, String message) throws JsonProcessingException {
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
