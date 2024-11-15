package com.w.ever.files.manager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {

    String message() default "This value must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // Specify which field this uniqueness should apply to
    String fieldName();

    // Specify which entity class the uniqueness check is for
    Class<?> entity();
}
