package com.api.ouimouve.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * status of a vehicle
 *
 */
@Getter
@AllArgsConstructor
public enum VehicleStatus {

        /**
         * The vehicle is not available for booking.
         */
        DISABLED("DISABLED"),

        /**
         * The vehicle is available for booking.
         */
        ENABLED("ENABLED"),

        /**
         * The vehicle is booked.
         */
        BOOKED ("BOOKED");

        private final String status;
    }
