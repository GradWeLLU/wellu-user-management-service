package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.MealLogRequestDto;
import com.wellu.usermanagement.dto.response.MealLogResponseDto;
import com.wellu.usermanagement.entity.MealLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MealLogEntryMapper.class})
public interface MealLogMapper {

    MealLogResponseDto toDto(MealLog entity);

    MealLog toEntity(MealLogRequestDto dto);
}
