package com.w.ever.files.manager.aspects;

import com.w.ever.files.manager.responses.SuccessApiResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiResponseAspect {
    @Around("execution(* com.w.ever.files.manager.controllers..*(..))")
    public Object formatResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // Proceed with the method execution and get the original result
            Object result = joinPoint.proceed();

            HttpHeaders headers = new HttpHeaders();

            SuccessApiResponse successApiResponse = new SuccessApiResponse(result, "Request processed successfully");

            // Wrap the result in the ResponseWrapper and return it
            return new ResponseEntity<>(successApiResponse, headers, HttpStatus.OK);
        } catch (Exception ex) {
            // Handle exceptions and return a wrapped error response
            HttpHeaders headers = new HttpHeaders();

            SuccessApiResponse successApiResponse = new SuccessApiResponse(ex.getStackTrace(), "Error");

            // Wrap the result in the ResponseWrapper and return it
            return new ResponseEntity<>(successApiResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
