package com.okta.developer.auth0.mappers;

import com.okta.developer.auth0.dtos.UserDto;
import com.okta.developer.auth0.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
}
