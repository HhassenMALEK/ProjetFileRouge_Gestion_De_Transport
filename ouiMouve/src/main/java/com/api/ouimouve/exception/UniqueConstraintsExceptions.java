package com.api.ouimouve.exception;

import lombok.Getter;
import java.util.List;

@Getter
public class UniqueConstraintsExceptions extends RuntimeException {
    private final List<ValidationErrorResponse.FieldError> errors;

    public UniqueConstraintsExceptions(List<ValidationErrorResponse.FieldError> errors) {
        this.errors = errors;
    }
}