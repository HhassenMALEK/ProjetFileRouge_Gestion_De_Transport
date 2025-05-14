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
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED"),
    CANCELLED("CANCELLED"),
    BOOKING_OPEN("BOOKING_OPEN"),
    BOOKING_FULL("BOOKING_FULL");

    private final String value;

    /**
     * Get the string value of the enum constant.
     * @param value The string value to match.
     * @return The corresponding CarPoolingStatus enum constant.
     */
    public static CarPoolingStatus fromValue(String value) {
        for (CarPoolingStatus status : values()) {
            if (status.getValue().equalsIgnoreCase(value)) { // Utilise le getter généré par Lombok
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value + ", Allowed values are " + java.util.Arrays.toString(values()));
    }
}