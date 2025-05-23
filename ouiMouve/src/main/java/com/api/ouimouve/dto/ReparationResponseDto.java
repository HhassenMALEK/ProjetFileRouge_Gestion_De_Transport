package com.api.ouimouve.dto;


import lombok.Data;

import java.util.Date;

/**
 *
 *  * Data Transfer Object (DTO) for Reparation.
 *  * This class is used to transfer data between the application and the client (Database response)
 *  * It contains fields for the repair ID, start date, end date, and motive, and the ID of the vehicule concerned.
 */
@Data
public class ReparationResponseDto {

    private Long id;
    private Date start;
    private Date end;
    private String motive;
    private ServiceVehicleDto vehicle;
}
