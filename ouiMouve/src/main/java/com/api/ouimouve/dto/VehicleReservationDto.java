package com.api.ouimouve.dto;

import com.api.ouimouve.bo.ServiceVehicle;
import com.api.ouimouve.bo.User;
import com.api.ouimouve.bo.Vehicle;
import com.api.ouimouve.enumeration.VehicleStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

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
