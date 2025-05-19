package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.Reparation;
import com.api.ouimouve.dto.ReparationCreateDto;
import com.api.ouimouve.dto.ReparationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapper interface for converting between Reparation and ReparationDto objects.
 */
@Mapper(componentModel = "spring")
public interface ReparationMapper {


    //Mapping Reparation -> ReparationResponseDto

    @Mapping(target ="vehicle.id", source = "serviceVehicle.id")
    @Mapping(target ="vehicle.immatriculation", source = "serviceVehicle.immatriculation")
    @Mapping(target ="vehicle.modelMark", source = "serviceVehicle.model.mark")
    @Mapping(target ="vehicle.modelName", source = "serviceVehicle.model.modelName")
    @Mapping(target ="vehicle.siteName", source = "serviceVehicle.site.name")
    @Mapping(target ="vehicle.status", source = "serviceVehicle.status")

    ReparationResponseDto toDto(Reparation reparation);

    // Mapping ReparationCreateDto -> Reparation
    // ignored because manual add in the service, before saving
    @Mapping(target = "serviceVehicle", ignore = true)
    Reparation toEntity(ReparationCreateDto dto);




}
