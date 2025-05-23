package com.api.ouimouve.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ImmatriculationValidator implements ConstraintValidator<ValidImmatriculation, String> {
    private static final Pattern IMMATRICULATION_PATTERN = Pattern.compile(
            "^[A-Za-z]{2}[0-9]{3}[A-Za-z]{2}$"
    );

    @Override
    public boolean isValid(String immatriculation, ConstraintValidatorContext context) {
        if (immatriculation == null) {
            return false;
        }
        return IMMATRICULATION_PATTERN.matcher(immatriculation).matches();
    }
}