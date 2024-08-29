package com.example.notification.global.service;

import com.example.notification.config.EnvironmentConfig;
import com.example.notification.exception.AESEncryptionException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

public class AESEncryptionService {
    private static final String AES = "AES";

    private static final EnvironmentConfig environmentConfig = new EnvironmentConfig();

    private AESEncryptionService() {
        // Static class
    }

    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(environmentConfig.get("AES_CBC_PKCS5PADDING"));

            SecretKeySpec secretKeySpec = new SecretKeySpec(environmentConfig.get("ENCRYPTION_KEY").getBytes(), AES);
            AlgorithmParameterSpec iv = new IvParameterSpec(environmentConfig.get("FIXED_IV").getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

            byte[] cipherText = cipher.doFinal(plainText.getBytes());

            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new AESEncryptionException("Error occurred during encryption", e);
        }
    }

    public static String decrypt(String cipherText) {
        try {
            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);

            Cipher cipher = Cipher.getInstance(environmentConfig.get("AES_CBC_PKCS5PADDING"));

            SecretKeySpec secretKeySpec = new SecretKeySpec(environmentConfig.get("ENCRYPTION_KEY").getBytes(), AES);
            AlgorithmParameterSpec iv = new IvParameterSpec(environmentConfig.get("FIXED_IV").getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

            byte[] plainText = cipher.doFinal(cipherTextBytes);
            return new String(plainText);
        } catch (Exception e) {
            throw new AESEncryptionException("Error occurred during decryption", e);
        }
    }
}
