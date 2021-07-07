package com.test_assignment.filters.model.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CriteriaComparisonValidator.class)
@Target( {ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CriteriaComparisonConstraint {
    String message() default "Criteria comparison must correspond to type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}