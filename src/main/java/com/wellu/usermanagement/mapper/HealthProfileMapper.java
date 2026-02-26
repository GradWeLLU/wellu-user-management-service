package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.response.HealthProfileResponseDto;
import com.wellu.usermanagement.entity.HealthProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {InjuryMapper.class, AllergyMapper.class, MedicationMapper.class}
)
public interface HealthProfileMapper {

    @Mapping(target = "injuries", source = "injuries")       // List<Injury> → List<InjuryDto>
    @Mapping(target = "allergies", source = "allergies")     // List<Allergy> → List<AllergyDto>
    @Mapping(target = "medications", source = "medications") // List<Medication> → List<MedicationDto>
    @Mapping(target = "userProfileId", source = "userProfile.id")
    HealthProfileResponseDto toResponse(HealthProfile healthProfile);
}