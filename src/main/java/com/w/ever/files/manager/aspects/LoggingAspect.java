package com.w.ever.files.manager.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Before Advice to log method arguments
    @Before("@annotation(LogMe)")
    public void logMethodArguments(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        System.out.println("Method: " + methodName + " called with arguments: " + java.util.Arrays.toString(arguments));
    }

    // AfterReturning Advice to log method return value
    @AfterReturning(value = "@annotation(LogMe)", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();

        System.out.println("Method: " + methodName + " returned: " + result);
    }
}