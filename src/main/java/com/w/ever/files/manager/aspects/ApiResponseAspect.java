package com.w.ever.files.manager.aspects;

import com.w.ever.files.manager.responses.ApiErrorResponse;
import com.w.ever.files.manager.responses.SuccessApiResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Aspect
@Component
public class ApiResponseAspect {
    @Around("execution(* com.w.ever.files.manager.controllers..*(..))")
    @ResponseBody
    public Object formatResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // Proceed with the method execution and get the original result
            Object result = joinPoint.proceed();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json ");

            SuccessApiResponse successApiResponse = new SuccessApiResponse(result, "Request processed successfully");

            // Wrap the result in the ResponseWrapper and return it
            return new ResponseEntity<>(successApiResponse, headers, HttpStatus.OK);
        } catch (Exception ex) {
            // Handle exceptions and return a wrapped error response
            HttpHeaders headers = new HttpHeaders();

            ApiErrorResponse successApiResponse = new ApiErrorResponse(LocalDateTime.now(), "Error", List.of(ex.toString()));

            // Wrap the result in the ResponseWrapper and return it
            return new ResponseEntity<>(successApiResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
