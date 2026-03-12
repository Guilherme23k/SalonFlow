package com.salonflow.backend.validations.interfaces;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Annotation;

public class BrazilianPhoneValidator implements ConstraintValidator<BRPhoneNumber, String> {

    private static final String PHONE_REGEX = "^\\d{2}9\\d{8}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {

        if (value == null) {
            return false;
        }

        return value.matches(PHONE_REGEX);
    }


    @Override
    public void initialize(BRPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
