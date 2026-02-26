package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.CreateAllergyRequest;
import com.wellu.usermanagement.dto.response.AllergyResponseDto;
import com.wellu.usermanagement.entity.Allergy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AllergyMapper {

    // Request DTO → Entity
    Allergy toEntity(CreateAllergyRequest dto);

    // Entity → Response DTO
    AllergyResponseDto toDto(Allergy allergy);
}