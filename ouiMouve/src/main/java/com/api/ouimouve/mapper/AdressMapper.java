package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.Adress;
import com.api.ouimouve.dto.AdressDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between Adress and AdressDto objects.
 */
@Mapper(componentModel = "spring")
public interface AdressMapper {

    AdressMapper MAPPER = Mappers.getMapper(AdressMapper.class);
    Adress toAdress(AdressDto adressDto);
    AdressDto toAdressDto(Adress adress);

}
