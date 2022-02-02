package edu.sombra.cms.domain.annotation.validator;


import edu.sombra.cms.domain.annotation.MarkRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static edu.sombra.cms.domain.enumeration.GradingSystem.PERCENTAGE;

public class MarkRangeValidator implements ConstraintValidator<MarkRange, Integer> {

    @Override
    public void initialize(MarkRange markRange) {
    }

    @Override
    public boolean isValid(Integer markField, ConstraintValidatorContext cxt) {
        return markField != null && PERCENTAGE.getMarkRange().isValidIntValue(markField);
    }

}