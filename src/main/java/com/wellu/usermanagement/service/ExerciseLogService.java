package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.ExerciseLogEntryRequestDto;
import com.wellu.usermanagement.dto.request.ExerciseLogRequestDto;
import com.wellu.usermanagement.dto.response.ExerciseLogResponseDto;
import com.wellu.usermanagement.entity.ExerciseLog;
import com.wellu.usermanagement.entity.ExerciseLogEntry;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.exception.ResourceNotFoundException;
import com.wellu.usermanagement.exception.UserNotFoundException;
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
                        new UserNotFoundException("User not found"));

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
                        new ResourceNotFoundException("Exercise log not found"));

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
                        new ResourceNotFoundException("Exercise log not found"));

        exerciseLogRepository.delete(log);
    }

    @Transactional
    public void deleteEntry(UUID entryId, UUID userId) {
        ExerciseLogEntry entry = exerciseEntryRepository
                .findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise log entry not found"));

        if (!entry.getExerciseLog().getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Exercise log entry not found for this user");
        }

        ExerciseLog log = entry.getExerciseLog();
        log.getEntries().remove(entry);

        exerciseEntryRepository.delete(entry);
    }
}
