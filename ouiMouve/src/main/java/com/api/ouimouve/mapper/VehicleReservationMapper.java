package com.api.ouimouve.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.api.ouimouve.bo.ServiceVehicle;
import com.api.ouimouve.bo.User;
import com.api.ouimouve.bo.VehicleReservation;
import com.api.ouimouve.dto.VehicleReservationCreateDto;
import com.api.ouimouve.dto.VehicleReservationDto;
import com.api.ouimouve.repository.UserRepository;
import com.api.ouimouve.repository.VehicleRepository;

/**
 * Mapper for converting between VehicleReservation entities and their DTO
 * representations.
 */
@Mapper(componentModel = "spring")
public abstract class VehicleReservationMapper {

    @Autowired
    protected VehicleRepository vehicleRepository;

    @Autowired
    protected UserRepository userRepository;

    public abstract VehicleReservation toVehicleReservation(VehicleReservationDto reservationDto);

    public abstract VehicleReservationDto toVehicleReservationDto(VehicleReservation reservation);

    @Mapping(target = "serviceVehicle", source = "serviceVehicleId", qualifiedByName = "serviceVehicleIdToEntity")
    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToEntity")
    public abstract VehicleReservation toVehicleReservation(VehicleReservationCreateDto createDto);

    @Named("serviceVehicleIdToEntity")
    protected ServiceVehicle mapServiceVehicle(Long id) {
        if (id == null)
            return null;
        return vehicleRepository.findById(id)
                .map(vehicle -> (ServiceVehicle) vehicle)
                .orElse(null);
    }

    @Named("userIdToEntity")
    protected User mapUser(Long id) {
        if (id == null)
            return null;
        return userRepository.findById(id).orElse(null);
    }
}