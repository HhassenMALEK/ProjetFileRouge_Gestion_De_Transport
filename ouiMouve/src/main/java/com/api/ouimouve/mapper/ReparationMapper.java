package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.Reparation;
import com.api.ouimouve.dto.ReparationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * Mapper interface for converting between Reparation and ReparationDto objects.
 */
@Mapper(componentModel = "spring")
public interface ReparationMapper {

    Reparation toReparation(ReparationDto reparationDto);
    ReparationDto toReparationDto(Reparation reparation);

}
