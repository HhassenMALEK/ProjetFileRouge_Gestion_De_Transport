package com.api.ouimouve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exception handler class for handling exceptions in the application.
 */
@ControllerAdvice
@Slf4j
public class ExceptionsHandler {
    /**
     * Handles RessourceNotFoundException and returns a 404 Not Found response.
     *
     * @param ex the RessourceNotFoundException
     * @return ResponseEntity with a 404 status and the exception message
     */
    @ExceptionHandler(value = {
        RessourceNotFoundException.class
    })
    public ResponseEntity<String> handleRessourceNotFoundException(RessourceNotFoundException ex) {
        log.error(null, ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles MethodArgumentNotValidException and returns a 400 Bad Request response.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles InvalidRessourceException and returns a 400 Bad Request response.
     *
     * @param ex the InvalidRessourceException
     * @return ResponseEntity with a 400 status and the exception message
     */
    @ExceptionHandler(value = {
        InvalidRessourceException.class
    })
    public ResponseEntity<String> handleInvalidRessourceException(InvalidRessourceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /** 
     * Handles InvalidRequestException and returns a 400 Bad Request response.
     *
     * @param ex the InvalidRequestException
     * @return ResponseEntity with a 400 status and the exception message
     */
    @ExceptionHandler(value = {
        InvalidRequestException.class
    })
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException ex) {
        log.error(null, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    /**
     * Handles InvalidCredentialsException and returns a 401 Unauthorized response.
     *
     * @param ex the InvalidCredentialsException
     * @return ResponseEntity with a 401 status and the exception message
     */
    @ExceptionHandler(value = {
            IllegalArgumentException.class
    })
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    /**
     * Handles ReservationConflictException and returns a 409 Conflict response.
     *
     * @param ex the ReservationConflictException
     * @return ResponseEntity with a 409 status and the exception message
     */
    @ExceptionHandler(value = {
            ReservationConflictException.class
    })
    public ResponseEntity<String> handleReservationConflictException(ReservationConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Handles UniqueConstraintsExceptions and returns a 409 Conflict response.
     * @param ex the UniqueConstraintsExceptions
     * @return ResponseEntity with a 409 status and the list of field errors
     */
    @ExceptionHandler(UniqueConstraintsExceptions.class)
    public ResponseEntity<List<ValidationErrorResponse.FieldError>> handleUniqueConstraintViolation(UniqueConstraintsExceptions ex) {
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.CONFLICT);
    }

    /**
     * Handles UserException and returns a 400 Bad Request response.
     * @param ex the UserException
     * @return ResponseEntity with a 400 status and an empty ValidationErrorResponse
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ValidationErrorResponse> handleUserException(UserException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<String> handleEmptyListException(EmptyListException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }
}
