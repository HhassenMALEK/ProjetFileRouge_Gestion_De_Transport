package com.api.ouimouve.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhotoURLValidator implements ConstraintValidator<ValidPhotoURL, String> {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^https?://.*\\.(jpg|png|svg)$",
            Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null) {
            return false;  // null n'est pas valide car le champ est marqué nullable=false
        }
        return URL_PATTERN.matcher(url).matches();
    }
}