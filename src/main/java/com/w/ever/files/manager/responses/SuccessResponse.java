package com.w.ever.files.manager.responses;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class SuccessResponse extends ResponseEntity<Object> {
    public SuccessResponse(Object body, HttpStatusCode status) {
        super(prepareBody(body), status);
    }
    public SuccessResponse() {
        super(prepareBody(""), HttpStatusCode.valueOf(200));
    }
    public SuccessResponse(Object body, int status) {
        super(prepareBody(body), null, status);
    }
    public SuccessResponse(Object body,String message, int status) {
        super(prepareBody(body,message), null, status);
    }
    public SuccessResponse(Object body,String message) {
        super(prepareBody(body,message),HttpStatusCode.valueOf(200));
    }
    public SuccessResponse(Object body) {
        super(prepareBody(body),HttpStatusCode.valueOf(200));
    }
    private static Object prepareBody(Object data,String message){
        return new HashMap<String,Object>(){{
            put("message",message);
            put("data",data);
        }};
    }
    private static Object prepareBody(Object data){
        return new HashMap<String,Object>(){{
            put("message","Success");
            put("data",data);
        }};
    }
}
