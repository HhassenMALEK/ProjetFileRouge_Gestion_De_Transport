package com.api.ouimouve.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO used to create a Site.
 * Contains minimal information needed for creation.
 */
@Data
public class SiteCreateDto {
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
