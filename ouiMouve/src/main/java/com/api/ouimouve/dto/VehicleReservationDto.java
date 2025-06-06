package com.api.ouimouve.dto;

import java.util.Date;

import com.api.ouimouve.enumeration.VehicleStatus;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for Reservation
 * This class is used to transfer data between the application and the client.
 * It contains fields for the repair ID, start date, end date, and motive.
 */
@Data
public class VehicleReservationDto {
    private long id;
    private ServiceVehicleDto serviceVehicle;
    private UserDto user;
    private Date start;
    private Date end;
    private VehicleStatus status;
}
