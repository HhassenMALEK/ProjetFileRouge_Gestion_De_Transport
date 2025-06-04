package com.api.ouimouve.dto;

import java.util.Date;

import com.api.ouimouve.enumeration.CarPoolingStatus;

import lombok.Data;

/**
 * Dto used to return carpooling information to the client.
 */
@Data
public class CarPoolingResponseDto {
    /** Unique identifier for the carpooling. */
    private Long id;
    /** The date and time when the carpooling is scheduled to start. */
    private Date departure;
    /** The time when the carpooling is scheduled to start. */
    private CarPoolingStatus status;
    /** Duration of the carpooling in minutes. */
    private Integer durationInMinutes;
    /** Distance of the carpooling in kilometers. */
    private Integer distance;

    /** Adress where the carpooling starts. */
    private SiteResponseDto departureSite ;
    /** Adress where the carpooling ends. */
    private SiteResponseDto destinationSite;

    private VehicleDto vehicle;
    /** User who organizes the carpooling. */
    private Long organizerId;
    private Integer participantCount;
}
