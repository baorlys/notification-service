package com.example.notification.interceptor;

import com.example.notification.dto.CredentialDTO;
import com.example.notification.global.service.CommonService;
import com.example.notification.service.CredentialService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CredentialInterceptor implements HandlerInterceptor {
    CredentialService credentialService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler)
            throws Exception {
        String apiKey = request.getHeader("x-api-key");
        String secretKey = request.getHeader("x-secret-key");

        CommonService.throwIsNotValid(!isValidCredential(apiKey, secretKey), "Invalid API Key or Secret Key");

        request.setAttribute("apiKey", apiKey);
        request.setAttribute("secretKey", secretKey);
        return true;
    }

    private boolean isValidCredential(String apiKey, String secretKey) {
        return credentialService.isValidCredential(new CredentialDTO(apiKey, secretKey));
    }
}
