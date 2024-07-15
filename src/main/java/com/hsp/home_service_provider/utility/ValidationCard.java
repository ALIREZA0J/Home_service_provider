package com.hsp.home_service_provider.utility;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidCard.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationCard {
    String message() default "Invalid Card Number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
