package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.Model;
import com.api.ouimouve.dto.ModelDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between Model and ModelDto objects.
 */
@Mapper(componentModel = "spring")
public interface ModelMapper {
    ModelMapper MAPPER = Mappers.getMapper(ModelMapper.class);

    Model toModel(ModelDto modelDto);
    ModelDto toModelDto(Model model);
}