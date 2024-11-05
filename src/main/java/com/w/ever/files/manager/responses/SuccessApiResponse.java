package com.w.ever.files.manager.responses;

import org.springframework.http.ResponseEntity;

public class SuccessApiResponse {
    private final String message;
    private final Object data;

    public SuccessApiResponse(Object data, String message) {
        this.message = message;
        this.data = data;
    }
}
