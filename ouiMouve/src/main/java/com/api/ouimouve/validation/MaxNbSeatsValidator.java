package com.api.ouimouve.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxNbSeatsValidator implements ConstraintValidator<ValidMaxNbSeats, Integer> {
    
    private int maxSeats;
    
    @Override
    public void initialize(ValidMaxNbSeats constraintAnnotation) {
        this.maxSeats = constraintAnnotation.value();
    }
    
    @Override
    public boolean isValid(Integer nbSeats, ConstraintValidatorContext context) {
        if (nbSeats == null) {
            return true;
        }
        return nbSeats <= maxSeats;
    }
}