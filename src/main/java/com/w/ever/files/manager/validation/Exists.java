package com.w.ever.files.manager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {

    String message() default "The specified value does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // Specify the field name to check for existence
    String fieldName();

    // Specify the entity class to check against
    Class<?> entity();
}
