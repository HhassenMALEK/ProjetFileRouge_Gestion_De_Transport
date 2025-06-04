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
public class ServiceVehicleCreateDto extends VehicleDto {
    /**
     * Current status of the service vehicle.
     */
    private VehicleStatus status;

    /**
     * ID of the model associated with this service vehicle.
     */
    private Long modelId;


    /**
     * ID of the site where this vehicle is located.
     */
    private Long siteId;


}