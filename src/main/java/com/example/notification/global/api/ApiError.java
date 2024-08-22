package com.example.notification.global.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError extends ApiResponse{
    String path;

    public ApiError(String message, String path) {
        super(message);
        this.path = path;
    }

}