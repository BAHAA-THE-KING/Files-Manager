package com.w.ever.files.manager.responses;

public class SuccessApiResponse {
    private String message;
    private Object data;

    public SuccessApiResponse(Object data, String message) {
        this.message = message;
        this.data = data;
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
}
