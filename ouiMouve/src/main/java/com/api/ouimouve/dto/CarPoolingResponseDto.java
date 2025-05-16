package com.api.ouimouve.dto;

import com.api.ouimouve.bo.Vehicle;
import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.Date;

/**
 * Dto for CarPooling entity.
 */
@Data
public class CarPoolingResponseDto {
    /** Unique identifier for the carpooling. */
    private Long id;
    /** The date and time when the carpooling is scheduled to start. */
    private Date departure;
    /** The date and time when the carpooling is scheduled to arrive. */
    private Date arrival;
    /** The time when the carpooling is scheduled to start. */
    private CarPoolingStatus status;
    /** Duration of the carpooling in minutes. */
    private Integer durationInMinutes;
    /** Distance of the carpooling in kilometers. */
    private Integer distance;

    private AdressDto departureAddress ;
    private AdressDto destinationAddress;

    //pour voir l'objet carpooling comme un arbre
    @JsonIgnore
    private Vehicle vehicle;
}
