package com.example.notification.global.utils.key;

import java.security.SecureRandom;
import java.util.Base64;

public class ApiKeyGenerator implements IKeyGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();
    private static final int API_KEY_BYTE_LENGTH = 18;

    @Override
    public String generate() {
        byte[] randomBytes = new byte[API_KEY_BYTE_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
