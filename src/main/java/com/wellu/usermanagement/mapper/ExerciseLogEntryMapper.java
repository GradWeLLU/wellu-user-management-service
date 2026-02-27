package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.ExerciseLogEntryRequestDto;
import com.wellu.usermanagement.dto.response.ExerciseLogEntryResponseDto;
import com.wellu.usermanagement.entity.ExerciseLogEntry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ExerciseSetMapper.class})
public interface ExerciseLogEntryMapper {

    ExerciseLogEntryResponseDto toDto(ExerciseLogEntry entity);

    ExerciseLogEntry toEntity(ExerciseLogEntryRequestDto dto);
}
