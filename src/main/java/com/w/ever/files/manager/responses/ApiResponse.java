package com.w.ever.files.manager.responses;

import java.util.ArrayList;

public class ApiResponse {
    protected String message;
    protected Object data;
    protected ArrayList<String> errors;

    public ApiResponse(Object data, String message, ArrayList<String> errors) {
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }
}
