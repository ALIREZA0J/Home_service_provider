package com.hsp.home_service_provider.utility;

import com.hsp.home_service_provider.model.Person;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Validation <T extends Person>{
     ValidatorFactory validatorFactory = jakarta.validation.Validation.buildDefaultValidatorFactory();
     Validator validator = validatorFactory.getValidator();


    public boolean validate(T entity) {

        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<T> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
    }
}
