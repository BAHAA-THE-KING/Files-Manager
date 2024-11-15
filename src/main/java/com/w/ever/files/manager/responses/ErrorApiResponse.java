package com.w.ever.files.manager.responses;

import java.util.List;

public class ErrorApiResponse extends ApiResponse {
    public ErrorApiResponse(Object data, List<String> errors) {
        super(data, "Error", errors);
    }
}
