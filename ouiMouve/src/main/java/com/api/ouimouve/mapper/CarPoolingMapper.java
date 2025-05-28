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
 * This interface uses MapStruct to generate the implementation for mapping between
 * CarPooling entity and CarPoolingCreateDto/CarPoolingResponseDto.
 */
@Mapper(componentModel = "spring")
public abstract class CarPoolingMapper {

    /**
     * Repository for carpooling-related data access (currently unused but available for future logic).
     */
    @Autowired
    private CarPoolingRepository carpoolingRepository;

    /**
     * Repository for vehicle-related data access (currently unused but can be used for enrichment logic).
     */
    @Autowired
    protected VehicleRepository vehicleRepository;

    /**
     * Converts a CarPoolingCreateDto into a CarPooling entity.
     * Only IDs are mapped here, assuming that associations will be resolved later in the service layer.
     *
     * @param dto the Dto containing the raw input from the client
     * @return the mapped CarPooling entity (with reference IDs only)
     */
    @Mapping(target = "departureAdress.id", source = "departureAddressId")
    @Mapping(target = "destinationAdress.id", source = "destinationAddressId")
    @Mapping(target = "organizer.id", source = "organizerId")
    @Mapping(target = "vehicle.id", source = "vehicleId")
    public abstract CarPooling toEntity(CarPoolingCreateDto dto);


    /**
     * Converts a CarPooling entity into a CarPoolingResponseDto.
     * Fields such as vehicle are ignored to avoid deep object graphs or circular dependencies.
     *
     * @param entity the CarPooling entity to convert
     * @return the corresponding DTO
     */
    @Mapping(target = "departureAddress", source = "departureAdress")
    @Mapping(target = "destinationAddress", source = "destinationAdress")
    @Mapping(target = "organizerId", source = "organizer.id")
    @Mapping(target = "vehicle", ignore = true) // pour éviter de charger tout un arbre d’objet inutile
    public abstract CarPoolingResponseDto toResponseDto(CarPooling entity);


}
