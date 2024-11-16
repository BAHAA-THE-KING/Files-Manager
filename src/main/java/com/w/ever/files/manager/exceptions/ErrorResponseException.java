package com.w.ever.files.manager.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorResponseException extends RuntimeException {
    protected int code=400;

    public ErrorResponseException(String message, int code) {
        super(message);
        this.code = code;
    }
    public ErrorResponseException(String message) {
        super(message);
    }

    public ErrorResponseException(String validationFailed, HttpStatus httpStatus, Object[] array) {
    }

    public int getCode() {
        return code;
    }
}
