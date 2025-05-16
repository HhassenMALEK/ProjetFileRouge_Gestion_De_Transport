package com.api.ouimouve.dto;

import com.api.ouimouve.enumeration.CarPoolingStatus;
import com.api.ouimouve.exception.InvalidRessourceException;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

/**
 * Dto for CarPooling entity.
 */
@Data
public class CarPoolingDto {
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

    private Long departureAddressId;
    private Long destinationAddressId;
    private Long vehicleId;
    private Long organizerId;


}
