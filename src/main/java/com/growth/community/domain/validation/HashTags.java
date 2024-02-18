package com.growth.community.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HashTagValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface HashTags {
    String message() default ValidationMessage.HASHTAGS_LENGTH;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}