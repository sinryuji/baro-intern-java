package com.sparta.barointernjava.user.presentation.annotation;

import com.sparta.barointernjava.user.presentation.validator.AdminCoderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = {AdminCoderValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAdminCode {

    String message() default "유효하지 않은 어드민 코드 입니다!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
