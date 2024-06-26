package com.hsp.home_service_provider.utility;

import com.hsp.home_service_provider.exception.DescriptionException;
import com.hsp.home_service_provider.exception.NotValidException;
import com.hsp.home_service_provider.exception.ProposedPriceException;
import com.hsp.home_service_provider.exception.SpecialistException;
import com.hsp.home_service_provider.model.Person;
import com.hsp.home_service_provider.model.enums.SpecialistStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class Validation <T extends Person>{
     ValidatorFactory validatorFactory = jakarta.validation.Validation.buildDefaultValidatorFactory();
     Validator validator = validatorFactory.getValidator();


    public void validate(T entity) {

        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<T> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new NotValidException("Data Not valid");
        }
    }

    public boolean checkPositiveNumber(Long number){
        return number > 0;
    }

    public boolean checkProposedDateNotBeforeToday(LocalDate time){
        return time.isBefore(LocalDate.now());
    }

    public void checkDescriptionNotBlank(String str){
        if (str.isBlank()) {
            throw new DescriptionException("Description is blank.");
        }
    }

    public void checkDescriptionPattern(String str){
        String regex = "^[A-Za-z\\s]+$";
        if (!Pattern.matches(regex, str)) {
            throw new DescriptionException("You can't use number.");
        }
    }
    public void checkProposedPriceNotLessThanSubService(Long pPrice, Long sPrice){
        if (pPrice == null)
            return;
        if (pPrice<sPrice)
            throw new ProposedPriceException("Proposed Price is less than sub-service price.");
    }

    public void checkSpecialistStatusIfItWasOtherThanAcceptedThrowException(SpecialistStatus specialistStatus){
        if (!specialistStatus.equals(SpecialistStatus.ACCEPTED))
            throw new SpecialistException("Specialist status is not accepted");
    }
}
