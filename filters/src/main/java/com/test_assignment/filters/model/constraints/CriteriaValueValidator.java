package com.test_assignment.filters.model.constraints;

import com.test_assignment.filters.model.Criteria;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CriteriaValueValidator implements ConstraintValidator<CriteriaValueConstraint, Criteria> {
    @Override
    public void initialize(CriteriaValueConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Criteria s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.getType().equals(Criteria.Type.AMOUNT)) {
            return s.getNumberValue() != null;
        }
        else if (s.getType().equals(Criteria.Type.TITLE)) {
            return s.getTextValue() != null && s.getTextValue().length() > 0;
        }
        else if (s.getType().equals(Criteria.Type.DATE)) {
            return s.getDateValue() != null;
        } else {
            return false;
        }
    }
}
