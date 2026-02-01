package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.InjuryRequestDto;
import com.wellu.usermanagement.dto.response.InjuryResponseDto;
import com.wellu.usermanagement.entity.Injury;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InjuryMapper {

    // Request DTO → Entity
    Injury toEntity(InjuryRequestDto dto);

    // Entity → Response DTO
    InjuryResponseDto toDto(Injury injury);
}
