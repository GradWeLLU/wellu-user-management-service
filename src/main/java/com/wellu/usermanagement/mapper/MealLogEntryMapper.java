package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.MealLogEntryRequestDto;
import com.wellu.usermanagement.dto.response.MealLogEntryResponseDto;
import com.wellu.usermanagement.entity.MealLogEntry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealLogEntryMapper {

    MealLogEntryResponseDto toDto(MealLogEntry entity);

    MealLogEntry toEntity(MealLogEntryRequestDto dto);
}
