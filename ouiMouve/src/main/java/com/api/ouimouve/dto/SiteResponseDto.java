package com.api.ouimouve.dto;

import lombok.Data;

/**
 * DTO used to return Site information to the client.
 */
@Data
public class SiteResponseDto {
    /** Unique identifier of the site */
    private Long id;
    /** Name of the site */
    private String name;

    /**
     * Label of this address
     */
    private String label;

    /**
     * City of this address
     */
    private String city;
    /**
     * Latitude of this address
     */
    private float latX;

    /**
     * Longitude of this address
     */
    private float longY;

}
