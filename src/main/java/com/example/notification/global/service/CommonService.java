package com.example.notification.global.service;

import javax.security.auth.login.CredentialException;

public class CommonService {
    private CommonService() {
        // Empty constructor
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




}
