package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.Site;
import com.api.ouimouve.dto.SiteDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface SiteMapper {
    SiteMapper MAPPER = Mappers.getMapper(SiteMapper.class);
    Site toSite(SiteDto siteDto);
    SiteDto toSiteDto(Site site);

}
