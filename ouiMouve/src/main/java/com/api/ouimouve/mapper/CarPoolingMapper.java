package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.CarPooling;
import com.api.ouimouve.dto.CarPoolingCreateDto;
import com.api.ouimouve.dto.CarPoolingResponseDto;
import com.api.ouimouve.repository.AdressRepository;
import com.api.ouimouve.repository.CarPoolingRepository;
import com.api.ouimouve.repository.VehicleRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper interface for CarPooling.
 */
@Mapper(componentModel = "spring")

public abstract class CarPoolingMapper {
    @Autowired
    private CarPoolingRepository carpoolingRepository;
    @Autowired
    protected VehicleRepository vehicleRepository;

    @Mapping(target = "departureAdress.id", source = "departureAddressId")
    @Mapping(target = "destinationAdress.id", source = "destinationAddressId")
    @Mapping(target = "vehicle.id", source = "vehicleId")
    public abstract CarPooling toEntity(CarPoolingCreateDto dto);



    @Mapping(target = "departureAddress", source = "departureAdress")
    @Mapping(target = "destinationAddress", source = "destinationAdress")
    @Mapping(target = "vehicle", ignore = true) // pour éviter de charger tout un arbre d’objet inutile
    public abstract CarPoolingResponseDto toResponseDto(CarPooling entity);

    /**
     * Enrichit le DTO de réponse après mappage si besoin (ex: durée calculée dynamiquement).
     */
    @AfterMapping
    protected void enrichResponse(@MappingTarget CarPoolingResponseDto dto, CarPooling entity) {
        if (dto.getDurationInMinutes() == null && entity.getDeparture() != null && entity.getArrival() != null) {
            long duration = (entity.getArrival().getTime() - entity.getDeparture().getTime()) / (1000 * 60);
            dto.setDurationInMinutes((int) duration);
        }
    }

}
