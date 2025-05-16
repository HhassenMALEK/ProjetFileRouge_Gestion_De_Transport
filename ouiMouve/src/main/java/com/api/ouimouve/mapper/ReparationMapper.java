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
    @Mapping(target = "vehicleId", source = "serviceVehicle.id")
    @Mapping(target = "vehicleImmatriculation", source = "serviceVehicle.immatriculation")
    @Mapping(target = "vehicleModelMark", source = "serviceVehicle.model.mark")
    @Mapping(target = "vehicleModelName", source = "serviceVehicle.model.mark")
    @Mapping(target = "vehicleSiteName", source = "serviceVehicle.site.name")
//    @Mapping(source = "serviceVehicle.model.mark", target = "vehicleModelMark")
//    @Mapping(source = "serviceVehicle.model.name", target = "vehicleModelName")
//    @Mapping(source = "serviceVehicle.immatriculation", target = "vehicleImmatriculation")
//    @Mapping(source = "serviceVehicle.site.name", target = "vehicleSiteName")
     ReparationResponseDto toDto(Reparation reparation);

    // Mapping ReparationCreateDto -> Reparation
    // ignored because manual add in the service, before saving
    @Mapping(target = "serviceVehicle", ignore = true)
    Reparation toEntity(ReparationCreateDto dto);



}
