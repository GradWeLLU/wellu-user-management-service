package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.ExerciseLogEntryRequestDto;
import com.wellu.usermanagement.dto.request.ExerciseLogRequestDto;
import com.wellu.usermanagement.dto.response.ExerciseLogResponseDto;
import com.wellu.usermanagement.entity.ExerciseLog;
import com.wellu.usermanagement.entity.ExerciseLogEntry;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.exception.ResourceNotFoundException;
import com.wellu.usermanagement.mapper.ExerciseLogEntryMapper;
import com.wellu.usermanagement.mapper.ExerciseLogMapper;
import com.wellu.usermanagement.repository.ExerciseEntryRepository;
import com.wellu.usermanagement.repository.ExerciseLogRepository;
import com.wellu.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExerciseLogService {

    private final ExerciseLogRepository exerciseLogRepository;
    private final ExerciseEntryRepository exerciseEntryRepository;
    private final ExerciseLogMapper exerciseLogMapper;
    private final ExerciseLogEntryMapper exerciseLogEntryMapper;
    private final UserRepository userRepository;

    @Transactional
    public ExerciseLogResponseDto createLog(UUID userId,
                                            ExerciseLogRequestDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        ExerciseLog log = exerciseLogMapper.toEntity(dto);

        log.setUser(user);

        ExerciseLog saved = exerciseLogRepository.save(log);

        return exerciseLogMapper.toDto(saved);
    }

    @Transactional
    public ExerciseLogResponseDto addEntry(UUID logId,
                                           UUID userId,
                                           ExerciseLogEntryRequestDto entryDto) {

        ExerciseLog log = exerciseLogRepository
                .findByIdAndUser_Id(logId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ExerciseLog not found"));

        ExerciseLogEntry entry = exerciseLogEntryMapper.toEntity(entryDto);

        log.addEntry(entry);

        exerciseEntryRepository.save(entry);

        return exerciseLogMapper.toDto(log);
    }
    @Transactional
    public void deleteLog(UUID logId, UUID userId) {

        ExerciseLog log = exerciseLogRepository
                .findByIdAndUser_Id(logId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ExerciseLog not found"));

        exerciseLogRepository.delete(log);
    }
}
