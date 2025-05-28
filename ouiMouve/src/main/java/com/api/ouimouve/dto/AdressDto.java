package com.api.ouimouve.dto;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Reparation.
 * This class is used to transfer data between the application and the client.
 *
 */
@Data
public class AdressDto {

    private long id;
    private String label;
    private String city;
    private float latX;
    private float longY;

}
