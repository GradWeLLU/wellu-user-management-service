package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.ExerciseLogRequestDto;
import com.wellu.usermanagement.dto.response.ExerciseLogResponseDto;
import com.wellu.usermanagement.entity.ExerciseLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ExerciseLogEntryMapper.class})
public interface ExerciseLogMapper {

    ExerciseLogResponseDto toDto(ExerciseLog entity);

    ExerciseLog toEntity(ExerciseLogRequestDto dto);
}
