package com.api.ouimouve.mapper;

import com.api.ouimouve.bo.User;
import com.api.ouimouve.dto.UserDto;
import com.api.ouimouve.dto.UserWithPasswordDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDtoWithoutPassword(User user);
    UserWithPasswordDto toDtoWithPassword(User user);
    User toEntityWithPassword(UserWithPasswordDto userWithPasswordDto);
}
