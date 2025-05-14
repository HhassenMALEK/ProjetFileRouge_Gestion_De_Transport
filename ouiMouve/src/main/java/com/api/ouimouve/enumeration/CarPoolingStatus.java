package com.api.ouimouve.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration representing the status of a carpooling reservation.
 * Possible statuses include:
 * - IN_PROGRESS
 * - FINISHED
 * - CANCELLED
 * - BOOKING_OPEN
 * - BOOKING_FULL
 */
@Getter
@AllArgsConstructor
public enum CarPoolingStatus {

    /**
     * Indicates that the carpooling reservation is currently in progress.
     */
    IN_PROGRESS("IN_PROGRESS"),
    /**
     * Indicates that the carpooling reservation has been completed.
     */
    FINISHED("FINISHED"),

    /**
     * Indicates that the carpooling reservation has been cancelled.
     */
    CANCELLED("CANCELLED"),

    /**
     * Indicates that the carpooling reservation is open for booking.
     */
    BOOKING_OPEN("BOOKING_OPEN"),

    /**
     * Indicates that the carpooling reservation is full and no more bookings can be made.
     */
    BOOKING_FULL("BOOKING_FULL");

    private final String value;

}