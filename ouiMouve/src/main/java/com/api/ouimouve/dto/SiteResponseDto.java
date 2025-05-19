package com.api.ouimouve.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO used to return Site information to the client.
 */
@Data
public class SiteResponseDto {
    /** Unique identifier of the site */
    private Long id;
    /** Name of the site */
    private String name;
    /** Associated address as a full DTO */
    private AdressDto adress;
    /** List of associated vehicle IDs */
    private List<Long> vehicleIds;
}
