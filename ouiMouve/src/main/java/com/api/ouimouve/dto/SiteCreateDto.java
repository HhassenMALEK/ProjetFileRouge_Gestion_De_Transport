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
    /** ID of the associated address */
    private Long adressId;
    /** List of vehicle IDs to associate with the site */
    private List<Long> vehicleIds;
}
