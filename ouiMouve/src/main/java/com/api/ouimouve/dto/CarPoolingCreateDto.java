package com.api.ouimouve.dto;

import com.api.ouimouve.enumeration.CarPoolingStatus;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.Date;

/**
 * Dto for CarPooling entity.
 */
@Data
public class CarPoolingCreateDto {
    /** Unique identifier for the carpooling. */
    @Nullable
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
    /** Adress where the carpooling starts. */
    private Long departureAddressId;
    /** Adress where the carpooling ends. */
    private Long destinationAddressId;
    /** vehicle used for the carpooling. */
    private Long vehicleId;
    private Long organizerId;

}
