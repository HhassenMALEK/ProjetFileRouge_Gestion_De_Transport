package com.api.ouimouve.dto;

import com.api.ouimouve.enumeration.VehicleStatus;
import lombok.Data;

/**
 * {@link ServiceVehicleDto} used for {@link ReparationResponseDto}
 */
@Data
public class ServiceVehicleDto {

    private Long id;
    private String immatriculation;
    private String modelMark;
    private String modelName;
    private String siteName;
    private VehicleStatus status;
}
