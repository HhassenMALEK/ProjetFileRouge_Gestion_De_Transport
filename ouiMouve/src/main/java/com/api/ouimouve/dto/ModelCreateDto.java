package com.api.ouimouve.dto;

import com.api.ouimouve.bo.ServiceVehicle;
import com.api.ouimouve.enumeration.VehicleCategory;
import com.api.ouimouve.validation.ValidMaxNbSeats;
import com.api.ouimouve.validation.ValidPhotoURL;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Model.
 * This class is used to transfer data between the application and the client.
 * It contains all fields corresponding to the Model entity.
 */
@Data
public class ModelCreateDto {
    private long id;
    private String modelName;
    private String mark;

    @ValidPhotoURL
    private String photoURL;
    private String motorType;
    private VehicleCategory category;
    private Integer CO2;

    @ValidMaxNbSeats(12)
    private Integer seatsModel;

}