package com.growth.community.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HashTagValidator implements ConstraintValidator<HashTags, String> {

    private final static int MAX_COUNT = 6;
    @Override
    public void initialize(HashTags constraintAnnotation) {
    }

    @Override
    public boolean isValid(String hashTags, ConstraintValidatorContext context) {
        return hashTags == null || hashTags.split("#").length <= MAX_COUNT;
    }
}