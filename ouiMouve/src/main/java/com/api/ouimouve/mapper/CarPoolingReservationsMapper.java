package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.CarPoolingReservations;
import com.api.ouimouve.dto.CarPoolingReservationsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for CarPoolingReservations.
 */
@Mapper(componentModel = "spring")
public interface CarPoolingReservationsMapper {
    CarPoolingReservationsMapper MAPPER = Mappers.getMapper(CarPoolingReservationsMapper.class);
    CarPoolingReservations toCarPoolingReservations(CarPoolingReservationsDTO carPoolingReservations);
    CarPoolingReservationsDTO toCarPoolingReservationsDTO(CarPoolingReservations carPoolingReservations);
}
