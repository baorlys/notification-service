package com.example.notification.global.service;

public class PhoneService {
    private static final String COUNTRY_CODE = "84";
    private PhoneService() {
        // static class
    }

    public static String prependCountryCode(String originalPhone) {
        CommonService.throwIsNull(originalPhone, "Phone number is null");

        if (originalPhone.startsWith(COUNTRY_CODE)) {
            return originalPhone;
        }

        long phone = Long.parseLong(originalPhone);
        return COUNTRY_CODE + phone;
    }
}
