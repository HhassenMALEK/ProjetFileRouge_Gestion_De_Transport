package com.api.ouimouve.dto;

import lombok.Data;

import java.util.List;
/**
 * DTO class for Site entity.
 */

@Data
public class SiteDto {
    private Long id;
    private String name;
    private String addressId;
    private List<Long> vehicleIds;
}
