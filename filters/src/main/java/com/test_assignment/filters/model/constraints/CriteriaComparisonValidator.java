package com.test_assignment.filters.model.constraints;

import com.test_assignment.filters.model.Criteria;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CriteriaComparisonValidator implements ConstraintValidator<CriteriaComparisonConstraint, Criteria> {
    @Override
    public void initialize(CriteriaComparisonConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Criteria criteria, ConstraintValidatorContext constraintValidatorContext) {
        Map<Criteria.Type, List<Criteria.Comparison>> comparisonMap = Map.of(
                Criteria.Type.AMOUNT, Arrays.asList(Criteria.Comparison.MORE, Criteria.Comparison.LESS, Criteria.Comparison.EQUAL),
                Criteria.Type.TITLE, Arrays.asList(Criteria.Comparison.STARTS, Criteria.Comparison.ENDS, Criteria.Comparison.EQUAL),
                Criteria.Type.DATE, Arrays.asList(Criteria.Comparison.FROM, Criteria.Comparison.TO, Criteria.Comparison.EQUAL)
        );
        return comparisonMap.get(criteria.getType()).contains(criteria.getComparison());
    }
}
