package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.UserRegisterRequest;
import com.wellu.usermanagement.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserEntity(UserRegisterRequest user);
}
