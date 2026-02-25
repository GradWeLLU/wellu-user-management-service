package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.GoalRequest;
import com.wellu.usermanagement.dto.response.GoalResponse;
import com.wellu.usermanagement.entity.Goal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    GoalResponse toResponse(Goal goal);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userProfile", ignore = true)
    @Mapping(target = "currentValue", constant = "0.0")
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "goalType", source = "type")
    Goal toEntity(GoalRequest request);
    List<GoalResponse> toResponseList(List<Goal> goals);
}




