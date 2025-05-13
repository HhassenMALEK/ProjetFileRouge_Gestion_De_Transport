package com.api.ouimouve.dto;

import lombok.Data;

import java.util.Date;
/**
 * Data Transfer Object (DTO) for Reparation.
 * This class is used to transfer data between the application and the client.
 * It contains fields for the repair ID, start date, end date, and motive.
 */
@Data
public class ReparationDto {
    private long id;
    private Date start;
    private Date end;
    private String motive;
    private long vehicleId;
}
