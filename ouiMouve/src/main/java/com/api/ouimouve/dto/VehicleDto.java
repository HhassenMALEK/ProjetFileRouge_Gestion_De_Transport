package com.api.ouimouve.dto;

import lombok.Data;

/**
 * Base Data Transfer Object for all vehicle types.
 * Contains common properties shared across vehicle subtypes.
 */
@Data
public class VehicleDto {
    /**
     * Unique identifier for the vehicle.
     */
    private long id;

    /**
     * License plate number of the vehicle.
     */
    private String immatriculation;

    /**
     * Number of seats available in the vehicle.
     */
    private Integer seats;
}