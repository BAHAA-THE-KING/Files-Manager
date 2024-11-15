package com.w.ever.files.manager.validation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private String fieldName;
    private Class<?> entityClass;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.entityClass = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Allow null values
        }

        try {
            String query = String.format("SELECT COUNT(e) FROM %s e WHERE e.%s = :value",
                    entityClass.getSimpleName(), fieldName);

            Long count = entityManager.createQuery(query, Long.class)
                    .setParameter("value", value)
                    .getSingleResult();

            return count == 0; // Valid if no existing records found
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception for debugging
            return false; // Return false or handle appropriately
        }
    }

}
