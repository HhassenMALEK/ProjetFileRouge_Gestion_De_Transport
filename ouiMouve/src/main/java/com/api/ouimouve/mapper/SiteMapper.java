package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.Site;
import com.api.ouimouve.dto.SiteDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between Site and SiteDto objects.
 */
@Mapper(componentModel = "spring")
public interface SiteMapper {

    /**
     * Create an instance of the SiteMapper
     */
    SiteMapper MAPPER = Mappers.getMapper(SiteMapper.class);

    /**
     * Convert a SiteDto to a Site object.
     *
     * @param siteDto the SiteDto to convert
     * @return the converted Site object
     */
    Site toSite(SiteDto siteDto);

    /**
     * Convert a Site object to a SiteDto.
     *
     * @param site the Site object to convert
     * @return the converted SiteDto
     */
    SiteDto toSiteDto(Site site);

}
