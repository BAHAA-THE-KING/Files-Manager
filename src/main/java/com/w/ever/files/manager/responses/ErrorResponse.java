package com.w.ever.files.manager.responses;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ErrorResponse extends ResponseEntity<Object> {

    // Constructor that accepts message, status (HttpStatusCode), and errors
    public ErrorResponse(String message, HttpStatusCode status, Object... errors) {
        super(prepareBody(errors, message), status);
    }

    // Constructor that accepts message, status (as Integer), and errors
    public ErrorResponse(String message, Integer status, Object... errors) {
        super(prepareBody(errors, message), HttpStatusCode.valueOf(status));
    }

    // Constructor that accepts only status (HttpStatusCode) and errors
    public ErrorResponse(HttpStatusCode status, Object... errors) {
        super(prepareBody(errors), status);
    }

    // Constructor that accepts only status (as Integer) and errors
    public ErrorResponse(int status, Object... errors) {
        super(prepareBody(errors), HttpStatusCode.valueOf(status));
    }

    // Constructor that accepts message and errors (defaults status to 400)
    public ErrorResponse(String message, Object... errors) {
        super(prepareBody(errors, message), HttpStatusCode.valueOf(400));
    }

    // Constructor that accepts only errors (defaults status to 400)
//    public ErrorResponse(Object... errors) {
//        super(prepareBody(errors), HttpStatusCode.valueOf(400));
//    }

    // Constructor that accepts message and status code (defaults errors to an empty array)
    public ErrorResponse(String message, int code) {
        super(prepareBody(new Object[0], message), HttpStatusCode.valueOf(code));
    }

    // Method to prepare the body with errors and a message
    private static Object prepareBody(Object errors, String message) {
        return new HashMap<String, Object>() {{
            put("message", message);
            put("errors", errors);
        }};
    }

    // Method to prepare the body with errors and a default message
    private static Object prepareBody(Object errors) {
        return new HashMap<String, Object>() {{
            put("message", "Something went wrong");
            put("errors", errors);
        }};
    }
}
