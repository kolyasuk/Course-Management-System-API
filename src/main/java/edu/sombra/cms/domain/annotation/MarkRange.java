package edu.sombra.cms.domain.annotation;

import edu.sombra.cms.domain.annotation.validator.MarkRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MarkRangeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MarkRange {
    String message() default "Mark is not in range";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
