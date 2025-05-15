package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.CarPoolingReservations;
import com.api.ouimouve.dto.CarPoolingReservationsCreateDTO;
import com.api.ouimouve.dto.CarPoolingReservationsResponseDTO;
import com.api.ouimouve.repository.CarPoolingReservationsRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper interface for CarPoolingReservations.
 */
@Mapper(componentModel = "spring")
public abstract class CarPoolingReservationsMapper {
    @Autowired
    private CarPoolingReservationsRepository repository;
    // Entité vers DTO de réponse (lecture)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "carPooling", source = "carPooling")
    public abstract CarPoolingReservationsResponseDTO toResponseDTO(CarPoolingReservations entity);

    // DTO de création vers entité
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "carPooling.id", source = "carPoolingId")
    public abstract CarPoolingReservations toEntity(CarPoolingReservationsCreateDTO dto);

    @AfterMapping
    protected void calculateParticipantCount(CarPoolingReservations entity, @MappingTarget CarPoolingReservationsResponseDTO dto) {
        if (entity.getCarPooling() != null && entity.getCarPooling().getId() != null) {
            dto.setParticipantCount(repository.countByCarPoolingId(entity.getCarPooling().getId()));
        }
    }
}