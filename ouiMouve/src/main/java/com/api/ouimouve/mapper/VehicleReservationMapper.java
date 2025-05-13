package com.api.ouimouve.mapper;


import com.api.ouimouve.bo.VehicleReservation;
import com.api.ouimouve.dto.VehicleReservationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between Reservation and ReservationDto objects.
 */
@Mapper(componentModel = "spring")
public interface VehicleReservationMapper {
    VehicleReservationMapper MAPPER = Mappers.getMapper(VehicleReservationMapper.class);
    VehicleReservation toVehicleReservation(VehicleReservationDto reservationDto);
    VehicleReservationDto toVehicleReservationDto(VehicleReservation reservation);

}
