package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.dto.CarPoolingDto;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between CarPooling and CarPoolingDto objects.
 */
@Mapper(componentModel = "spring")
public interface CarPoolingMapper {

    /**
     * Converts a CarPoolingDto to a CarPooling entity.
     *
     * @param carPoolingDto the DTO to convert
     * @return the corresponding CarPooling entity
     */
    CarPooling toEntity(CarPoolingDto carPoolingDto);

    /**
     * Converts a CarPooling entity to a CarPoolingDto.
     *
     * @param carPooling the entity to convert
     * @return the corresponding CarPoolingDto
     */
    CarPoolingDto toCarPoolingDto(CarPooling carPooling);
}
