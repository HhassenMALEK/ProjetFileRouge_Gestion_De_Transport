package com.api.ouimouve.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxNbSeatsValidator.class)
@Documented
public @interface ValidMaxNbSeats {

    int value() default 10;

    String message() default "Le modèle de véhicule ne peut posséder plus de {value} places assises";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}