package com.code.reviewer.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HashTagValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface HashTags {
    String message() default "Invalid hash tags";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}