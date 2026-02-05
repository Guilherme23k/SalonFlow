package com.salonflow.backend.validations.interfaces;

import com.salonflow.backend.validations.BrazilianPhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BrazilianPhoneValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BrazilianPhone {

    String message() default "Deve estar em padrao brasileiro";

    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};

}