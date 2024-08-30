package com.example.notification.global.service;

import javax.security.auth.login.CredentialException;

public class CommonService {
    private CommonService() {
        // Static class
    }

    public static void throwIsNull(Object obj, String msg) throws NullPointerException {
        if (obj == null) {
            throw new NullPointerException(msg);
        }
    }

    public static void throwIsNotExists(boolean isNotExists, String msg) throws CredentialException {
        if (isNotExists) {
            throw new CredentialException(msg);
        }
    }

    public static void throwIsNotValid(boolean isNotValid, String msg) throws CredentialException {
        if (isNotValid) {
            throw new CredentialException(msg);
        }
    }


    public static void throwIsOverSizeMessage(int length, int maxMessageSize, String msg) {
        if (length > maxMessageSize) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void throwArgumentException(boolean isNotValid, String msg) {
        if (isNotValid) {
            throw new IllegalArgumentException(msg);
        }
    }
}
