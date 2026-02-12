package com.salonflow.backend.validations.interfaces;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Annotation;

public class BrazilianPhoneValidator implements ConstraintValidator<BRPhoneNumber, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext ctx) {
        if (s == null || s.isBlank() || s.matches("^(1[1-9]|[2-9][0-9])$")) {
            return false;
        }

        return true;
    }


    @Override
    public void initialize(BRPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
