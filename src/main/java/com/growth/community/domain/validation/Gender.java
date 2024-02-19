package com.growth.community.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Gender {
    String message() default ValidationMessage.INVALID_GENDER;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
