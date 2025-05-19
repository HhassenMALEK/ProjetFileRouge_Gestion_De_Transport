package com.api.ouimouve.dto;

import com.api.ouimouve.enumeration.VehicleStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object for service vehicles.
 * Extends the base VehicleDto with service vehicle specific properties.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceVehicleDto extends VehicleDto {
    /**
     * Current status of the service vehicle.
     */
    private VehicleStatus status;

    /**
     * ID of the model associated with this service vehicle.
     */
    private Long modelId;


    /**
     * Brand of the model (optional, for display purposes).
     */
    private String mark;

    /**
     * ID of the site where this vehicle is located.
     */
    private Long siteId;

    /**
     * Name of the site (optional, for display purposes).
     */
    private String siteName;
}