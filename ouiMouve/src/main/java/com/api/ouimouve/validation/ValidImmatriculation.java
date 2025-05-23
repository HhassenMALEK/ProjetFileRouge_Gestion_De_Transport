package com.api.ouimouve.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImmatriculationValidator.class)
@Documented
public @interface ValidImmatriculation {
    String message() default "Le numéro d'immatriculation doit respecter le format: 2 lettres, 3 chiffres, 2 lettres";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}