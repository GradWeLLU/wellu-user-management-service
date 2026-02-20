package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.response.UserProfileResponse;
import com.wellu.usermanagement.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileResponse toResponse(User user);
}
