package com.api.ouimouve.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CarPoolingReservationStatus {

    /**
     * Indicates that the carpooling reservation is currently in progress.
     */
    BOOKED("IN_PROGRESS"),
    /**
     * Indicates that the carpooling reservation has been completed.
     */
    FINISHED("FINISHED"),

    /**
     * Indicates that the carpooling reservation has been cancelled.
     */
    CANCELLED("CANCELLED");


    private final String value;

}
