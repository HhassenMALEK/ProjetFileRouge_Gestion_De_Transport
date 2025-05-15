package com.api.ouimouve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler class for handling exceptions in the application.
 */
@ControllerAdvice
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
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
     * Handles IllegalArgumentException and returns a 400 Bad Request response.
     *
     * @param ex the IllegalArgumentException
     * @return ResponseEntity with a 400 status and the exception message
     */
    @ExceptionHandler(value = {
            IllegalArgumentException.class
    })
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
