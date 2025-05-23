package com.api.ouimouve.dto;

import com.api.ouimouve.enumeration.VehicleCategory;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for Model.
 * This class is used to transfer data between the application and the client.
 * It contains all fields corresponding to the Model entity.
 */
@Data
public class ModelDto {
    private long id;
    private String modelName;
    private String mark;
    private String photoURL;
    private String motorType;
    private VehicleCategory category;
    private Integer CO2;

    private Integer seatsModel;

}