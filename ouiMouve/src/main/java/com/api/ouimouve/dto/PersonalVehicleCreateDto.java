package com.api.ouimouve.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object for personal vehicles.
 * Extends the base VehicleDto with personal vehicle specific properties.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalVehicleCreateDto extends VehicleDto {
    /**
     * Color of the personal vehicle.
     */
    private String color;

    /**
     * Additional description of the personal vehicle.
     */
    private String description;

    /**
     * ID of the user who owns this personal vehicle.
     */
    private Long userId;

    /**
     * Username of the vehicle owner (optional, for display purposes).
     */
    private String userName;
}