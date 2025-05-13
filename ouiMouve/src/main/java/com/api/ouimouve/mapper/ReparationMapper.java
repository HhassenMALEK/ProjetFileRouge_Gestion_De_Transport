package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.Reparation;
import com.api.ouimouve.dto.ReparationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * Mapper interface for converting between Reparation and ReparationDto objects.
 */
@Mapper(componentModel = "spring")
public interface ReparationMapper {
    ReparationMapper MAPPER = Mappers.getMapper(ReparationMapper.class);
    Reparation toReparation(ReparationDto reparationDto);
    ReparationDto toReparationDto(Reparation reparation);

}
