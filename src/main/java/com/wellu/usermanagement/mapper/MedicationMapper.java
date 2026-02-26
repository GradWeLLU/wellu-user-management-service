package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.CreateMedicationRequest;
import com.wellu.usermanagement.dto.response.MedicationResponseDto;
import com.wellu.usermanagement.entity.HealthProfile;
import com.wellu.usermanagement.entity.Medication;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "healthProfile", source = "healthProfile")
    Medication toEntity(CreateMedicationRequest request, HealthProfile healthProfile);

    // Map to nested DTO
    MedicationResponseDto toDto(Medication medication);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "healthProfile", ignore = true)
    Medication updateEntityFromDto(CreateMedicationRequest request, @MappingTarget Medication medication);
}