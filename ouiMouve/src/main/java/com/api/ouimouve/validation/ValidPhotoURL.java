package com.api.ouimouve.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhotoURLValidator.class)
@Documented
public @interface ValidPhotoURL {
    String message() default "L'URL de la photo doit commencer par http:// ou https:// et se terminer par .jpg, .png ou .svg";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}