package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.response.UserProfileResponse;
import com.wellu.usermanagement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(source = "profile.age", target = "age")
    @Mapping(source = "profile.weight", target = "weight")
    @Mapping(source = "profile.height", target = "height")
    @Mapping(expression = "java(user.getProfile() != null && user.getProfile().getHeight() > 0 && user.getProfile().getWeight() > 0 ? user.getProfile().calculateBMI() : null)", target = "bmi")
    UserProfileResponse toResponse(User user);
}