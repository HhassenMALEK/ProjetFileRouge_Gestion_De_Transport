package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for CarPooling.
 * This interface uses MapStruct to generate the implementation for mapping between
 * CarPooling entity and CarPoolingCreateDto/CarPoolingResponseDto.
 */
@Mapper(componentModel = "spring")
public abstract class CarPoolingMapper {


    /**
     * Converts a CarPoolingCreateDto into a CarPooling entity.
     * Only IDs are mapped here, assuming that associations will be resolved later in the service layer.
     *
     * @param dto the Dto containing the raw input from the client
     * @return the mapped CarPooling entity (with reference IDs only)
     */
    @Mapping(target = "departureSite.id", source = "departureSiteId")
    @Mapping(target = "destinationSite.id", source = "destinationSiteId")
    @Mapping(target = "organizer.id", source = "organizerId")
    @Mapping(target = "vehicle.id", source = "vehicleId")
    public abstract CarPooling toEntity(CarPoolingCreateDto dto);


    /**
     * Converts a CarPooling entity into a CarPoolingResponseDto.
     * Fields such as vehicle are ignored to avoid deep object graphs or circular dependencies.
     *
     * @param entity the CarPooling entity to convert
     * @return the corresponding DTO
     */
    @Mapping(target = "departureSite", source = "departureSite")
    @Mapping(target = "destinationSite", source = "destinationSite")
    @Mapping(target = "organizerId", source = "organizer.id")
    @Mapping(target = "vehicle", source="vehicle") // pour éviter de charger tout un arbre d’objet inutile
    public abstract CarPoolingResponseDto toResponseDto(CarPooling entity);


}
