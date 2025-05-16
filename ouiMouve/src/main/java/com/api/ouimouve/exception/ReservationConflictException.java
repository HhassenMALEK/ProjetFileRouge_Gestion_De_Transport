package com.api.ouimouve.exception;

/**
 * Exception thrown when a reservation conflicts with an existing reservation or repair.
 * This happens when the requested period overlaps with an already booked period or scheduled repair.
 */
public class ReservationConflictException extends RuntimeException {
    public ReservationConflictException(String message) {
        super(message);
    }
}