package com.exo.authentification.mapper;

import com.exo.authentification.dto.SignUpDto;
import com.exo.authentification.dto.UserDto;
import com.exo.authentification.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
