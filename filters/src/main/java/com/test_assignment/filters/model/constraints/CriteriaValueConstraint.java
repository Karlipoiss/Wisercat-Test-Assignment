package com.test_assignment.filters.model.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CriteriaValueValidator.class)
@Target( {ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CriteriaValueConstraint {
    String message() default "Criteria value is mandatory";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}