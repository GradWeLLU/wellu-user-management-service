package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.ExerciseLogRequestDto;
import com.wellu.usermanagement.dto.response.ExerciseLogResponseDto;
import com.wellu.usermanagement.mapper.ExerciseLogMapper;
import com.wellu.usermanagement.repository.ExerciseEntryRepository;
import com.wellu.usermanagement.repository.ExerciseLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExerciseLogService {

    private final ExerciseLogRepository exerciseLogRepository;
    private final ExerciseEntryRepository exerciseEntryRepository;
    private final ExerciseLogMapper exerciseLogMapper;

    public ExerciseLogResponseDto create(ExerciseLogRequestDto dto) {
        // TODO: implement
        return null;
    }

    public List<ExerciseLogResponseDto> getAll() {
        // TODO: implement
        return List.of();
    }

    public ExerciseLogResponseDto getById(UUID id) {
        // TODO: implement
        return null;
    }

    public ExerciseLogResponseDto update(UUID id, ExerciseLogRequestDto dto) {
        // TODO: implement
        return null;
    }

    public void delete(UUID id) {
        // TODO: implement
    }
}
