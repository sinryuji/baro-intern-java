package com.sparta.barointernjava.user.presentation.validator;

import com.sparta.barointernjava.user.presentation.annotation.ValidAdminCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class AdminCoderValidator implements ConstraintValidator<ValidAdminCode, String> {

    @Value("${admin.code}")
    private String adminCode;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return adminCode.equals(value);
    }
}
