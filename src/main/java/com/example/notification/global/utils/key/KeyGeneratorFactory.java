package com.example.notification.global.utils.key;


import com.example.notification.enums.KeyType;

import java.util.EnumMap;
import java.util.Map;

public class KeyGeneratorFactory {
    static final Map<KeyType, IKeyGenerator> keyMap = new EnumMap<>(KeyType.class);

    static {
        keyMap.put(KeyType.API_KEY, new ApiKeyGenerator());
        keyMap.put(KeyType.SECRET_KEY, new SecretKeyGenerator());
    }

    private KeyGeneratorFactory() {
        // Hide the constructor
    }

    public static IKeyGenerator getKeyGenerator(KeyType keyType) {
        return keyMap.get(keyType);
    }
}
