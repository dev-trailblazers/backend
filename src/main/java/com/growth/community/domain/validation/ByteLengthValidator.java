package com.growth.community.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.UnsupportedEncodingException;

public class ByteLengthValidator implements ConstraintValidator<ByteLength, String> {

    private int max;
    private int min;

    @Override
    public void initialize(ByteLength constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        try {
            int byteLength = value.getBytes("UTF-8").length;
            return byteLength >= min && byteLength <= max;
        } catch (UnsupportedEncodingException e) {
            // 예외 처리, 필요에 따라 로깅 등 수행
            return false;
        }
    }
}
