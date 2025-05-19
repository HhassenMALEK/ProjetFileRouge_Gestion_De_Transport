package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.Model;
import com.api.ouimouve.bo.ServiceVehicle;
import com.api.ouimouve.dto.ModelCreateDto;
import com.api.ouimouve.dto.ModelDto;
import com.api.ouimouve.dto.ServiceVehicleDto;
import com.api.ouimouve.repository.ModelRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper interface for converting between Model and ModelDto objects.
 */
@Mapper(componentModel = "spring")
public abstract class ModelMapper {

    @Autowired
    protected ModelRepository modelRepository;

    /**
     * Converts a ModelDto to a Model entity
     */
    public abstract Model toModel(ModelDto modelDto);

    /**
     * Converts a ModelCreateDto to a Model entity
     */
    public abstract Model toModel(ModelCreateDto modelDto);

    /**
     * Converts a Model entity to a ModelDto
     */
    public abstract ModelDto toModelDto(Model model);

//    /**
//     * Converts a list of ServiceVehicle entities to a list of ServiceVehicleDto objects.
//     *
//     * @param serviceVehicles List of ServiceVehicle entities to convert
//     * @return List of ServiceVehicleDto objects
//     */
//    @Named("serviceVehiclesToDtos")
//    protected List<ServiceVehicleDto> mapServiceVehiclesToDtos(List<ServiceVehicle> serviceVehicles) {
//        if (serviceVehicles == null) {
//            return Collections.emptyList();
//        }
//
//        return serviceVehicles.stream()
//                .map(this::mapServiceVehicleToDto)
//                .collect(Collectors.toList());
//    }

    /**
     * Converts a single ServiceVehicle entity to a ServiceVehicleDto object.
     *
     * @param vehicle ServiceVehicle entity to convert
     * @return ServiceVehicleDto object
     */
    protected abstract ServiceVehicleDto mapServiceVehicleToDto(ServiceVehicle vehicle);

    /**
     * Convertit un ID de modèle en entité Model.
     *
     * @param id ID du modèle à récupérer
     * @return L'entité Model correspondante ou null
     */
    @Named("modelIdToEntity")
    protected Model mapModelIdToEntity(Long id) {
        if (id == null) return null;
        return modelRepository.findById(id).orElse(null);
    }
}