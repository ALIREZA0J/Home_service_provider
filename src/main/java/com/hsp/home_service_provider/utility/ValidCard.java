package com.hsp.home_service_provider.utility;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;

public class ValidCard implements ConstraintValidator<ValidationCard, Long> {
    @Override
    public void initialize(ValidationCard constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        long sum = 0;
        ArrayList<Long> longs = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Long numeric = (value % 10);
            longs.add(numeric);
            value = (value / 10);
        }
        for (int i = 0; i < longs.size(); i++) {
            if (i % 2 == 0 ){
                sum = sum + checkNumber(longs.get(i));
            } else{
                sum = sum + checkNumber((longs.get(i) * 2));
            }

        }
        return sum % 10 == 0;
    }
    private static Long checkNumber(Long l){
        if (l > 9){
            return (l - 9);
        } else return l;
    }
}
