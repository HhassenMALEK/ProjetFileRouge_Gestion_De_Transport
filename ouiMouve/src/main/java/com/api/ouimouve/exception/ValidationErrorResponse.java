package com.api.ouimouve.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ValidationErrorResponse {
    private int status;
    private List<FieldError> errors = new ArrayList<>();
    
    @Data
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String message;
    }
    
    public void addFieldError(String field, String message) {
        this.errors.add(new FieldError(field, message));
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}