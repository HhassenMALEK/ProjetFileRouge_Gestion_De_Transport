package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.Site;
import com.api.ouimouve.bo.ServiceVehicle;
import com.api.ouimouve.dto.SiteCreateDto;
import com.api.ouimouve.dto.SiteResponseDto;
import com.api.ouimouve.mapper.AdressMapper;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface for Site.
 * this interface uses MapStruct to generate the implementation for mapping between
 * Site entity and SiteCreateDto/SiteResponseDto.
 */
@Mapper(componentModel = "spring", uses = {AdressMapper.class})// cette dirctive indique à MapStruct d'utiliser AdressMapper
public interface SiteMapper {

    /**
     * convert SiteCreateDto to Site entity
     * @param dto the Dto containing the raw input from the client
     * @return the mapped Site entity (with reference IDs only)
     */
    @Mapping(source = "adressId", target = "adress.id")
    @Mapping(target = "vehiclesServices", ignore = true)
    Site toSite(SiteCreateDto dto);

    /**
     * Convert a Site entity into a SiteResponseDto.
     * includes the AdressMapper for adress mapping.
     */
    // AdressMapper s'en occupe
    @Mapping(source = "adress", target = "adress")
    // Extrait uniquement les IDs des véhicules pour alléger le DTO et éviter les références circulaires @Mapping(target = "vehicleIds", expression = "java(mapVehicleIds(site.getVehiclesServices()))")
    SiteResponseDto toSiteResponseDto(Site site);

    /**
     * Trnsforme a list of ServiceVehicle into a list of Long IDs.
     */
    default List<Long> mapVehicleIds(List<ServiceVehicle> vehicles) {
        if (vehicles == null) return null;
        return vehicles.stream()
                .map(ServiceVehicle::getId)
                .collect(Collectors.toList());
    }
}
