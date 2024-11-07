package com.w.ever.files.manager.responses;

import java.util.ArrayList;

public class ErrorApiResponse extends ApiResponse {
    public ErrorApiResponse(Object data, ArrayList<String> errors) {
        super(data, "Error", errors);
    }
}
