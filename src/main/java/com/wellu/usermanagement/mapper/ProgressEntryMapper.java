package com.wellu.usermanagement.mapper;

import com.wellu.usermanagement.dto.request.ProgressEntryCreateRequest;
import com.wellu.usermanagement.dto.response.ProgressEntryResponse;
import com.wellu.usermanagement.entity.ProgressEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ProgressEntryMapper {
    ProgressEntryResponse toResponse(ProgressEntry entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recordedAt", ignore = true)
    @Mapping(target = "userProfile", ignore = true)
    ProgressEntry toEntity(ProgressEntryCreateRequest request);
}
