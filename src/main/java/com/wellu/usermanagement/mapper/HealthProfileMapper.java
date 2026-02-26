package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.response.HealthProfileResponseDto;
import com.wellu.usermanagement.entity.HealthProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HealthProfileMapper {

    @Mapping(target = "injuryIds", source = "injuries", qualifiedByName = "extractIds")
    @Mapping(target = "allergyIds", source = "allergies", qualifiedByName = "extractIds")
    @Mapping(target = "medicationIds", source = "medications", qualifiedByName = "extractIds")
    @Mapping(target = "userProfileId", source = "userProfile.id")
    HealthProfileResponseDto toResponse(HealthProfile healthProfile);

    @Named("extractIds")
    static <T> List<UUID> extractIds(List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        }

        return entities.stream()
                .map(e -> {
                    try {
                        return (UUID) e.getClass().getMethod("getId").invoke(e);
                    } catch (Exception ex) {
                        throw new RuntimeException("Failed to extract ID", ex);
                    }
                })
                .collect(Collectors.toList());
    }
}