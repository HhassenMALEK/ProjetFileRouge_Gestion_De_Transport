package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between CarPooling and CarPoolingResponseDto objects.
 */
@Mapper(componentModel = "spring")
public interface CarPoolingMapper {


    CarPooling toEntity(CarPoolingCreateDto carPoolingCreateDto);

    CarPoolingResponseDto toResponseDto(CarPooling carPooling);
}
