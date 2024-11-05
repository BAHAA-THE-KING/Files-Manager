package com.w.ever.files.manager.aspects;

import com.w.ever.files.manager.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
public class AuthAspect {

    private final TokenService tokenService;

    public AuthAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Before("execution(* com.w.ever.files.manager.controllers..*(..)) && !execution(* com.w.ever.files.manager.controllers.AuthController..*(..))")
    public void checkAccessToken(JoinPoint joinPoint) throws Exception {
        // Access the HttpServletRequest from the current request context
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // Check for the access token in headers
            String accessToken = request.getHeader("Authorization");
//            if (accessToken == null || !tokenService.isValidToken(accessToken)) {
//                throw new SecurityException("Access denied: Invalid or missing access token");
//            }
        }
    }
}
