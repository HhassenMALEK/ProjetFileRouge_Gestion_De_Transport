package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.Site;
import com.api.ouimouve.dto.SiteCreateDto;
import com.api.ouimouve.dto.SiteResponseDto;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface for Site.
 * this interface uses MapStruct to generate the implementation for mapping between
 * Site entity and SiteCreateDto/SiteResponseDto.
 */
@Mapper(componentModel = "spring")
public interface SiteMapper {

    /**
     * convert SiteCreateDto to Site entity
     * @param dto the Dto containing the raw input from the client
     * @return the mapped Site entity (with reference IDs only)
     */


    Site toSite(SiteCreateDto dto);

    /**
     * Convert a Site entity into a SiteResponseDto.
     */
    SiteResponseDto toSiteResponseDto(Site site);


}
