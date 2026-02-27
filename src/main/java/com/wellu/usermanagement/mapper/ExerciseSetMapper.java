package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.ExerciseSetDto;
import com.wellu.usermanagement.entity.ExerciseSet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseSetMapper {

    ExerciseSetDto toDto(ExerciseSet entity);

    ExerciseSet toEntity(ExerciseSetDto dto);
}
