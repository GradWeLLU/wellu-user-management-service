package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.CreateInjuryRequest;
import com.wellu.usermanagement.dto.response.InjuryResponseDto;
import com.wellu.usermanagement.entity.Injury;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InjuryMapper {

    // Request DTO → Entity
    @Mapping(target = "id", ignore = true)
    Injury toEntity(CreateInjuryRequest dto);

    // Entity → Response DTO
    @Mapping(target = "isChronic", source = "chronic") // fix boolean naming
    InjuryResponseDto toDto(Injury injury);
}