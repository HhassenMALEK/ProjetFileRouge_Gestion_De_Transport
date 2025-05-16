package com.api.ouimouve.dto;

import lombok.Data;

import java.util.Date;

/**
 *
 *  * Data Transfer Object (DTO) for Reparation.
 *  * This class is used to transfer data between the application and the client when new data is created.
 *  * It contains fields for the repair ID, start date, end date, and motive.
 */
@Data
public class ReparationCreateDto {

    private Date start;
    private Date end;
    private String motive;
    private Long vehicleId;
}
