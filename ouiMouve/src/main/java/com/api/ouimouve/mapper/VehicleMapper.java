package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.PersonalVehicle;
import com.api.ouimouve.bo.ServiceVehicle;
import com.api.ouimouve.bo.Vehicle;
import com.api.ouimouve.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ModelMapper.class, SiteMapper.class})
public interface VehicleMapper {

    // Mappings de base pour Vehicle
    Vehicle toEntity(VehicleDto vehicleDto);
    VehicleDto toDto(Vehicle vehicle);

    // Mappings pour les sous-classes avec SubclassMapping (MapStruct 1.5+)
    @SubclassMapping(source = PersonalVehicleDto.class, target = PersonalVehicle.class)
    @SubclassMapping(source = ServiceVehicleDto.class, target = ServiceVehicle.class)
    Vehicle toEntityPolymorphic(VehicleDto dto);

    @SubclassMapping(source = PersonalVehicle.class, target = PersonalVehicleDto.class)
    @SubclassMapping(source = ServiceVehicle.class, target = ServiceVehicleDto.class, qualifiedByName = "serviceVehicleToDto")
    VehicleDto toDtoPolymorphic(Vehicle vehicle);

    // PersonalVehicle mappings
    PersonalVehicle toEntity(PersonalVehicleDto dto);
    PersonalVehicleDto toDto(PersonalVehicle entity);

    @Mapping(source = "userId", target = "user.id")
    PersonalVehicle toEntity(PersonalVehicleCreateDto dto);

    // ServiceVehicle mappings

    ServiceVehicle toEntity(ServiceVehicleDto dto);
    @Named("serviceVehicleToDto")
    ServiceVehicleDto toDto(ServiceVehicle entity);

    @Mapping(source = "modelId", target = "model.id")
    @Mapping(source = "siteId", target = "site.id")
    ServiceVehicle toEntity(ServiceVehicleCreateDto dto);
}