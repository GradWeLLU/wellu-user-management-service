package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.MealLogEntryRequestDto;
import com.wellu.usermanagement.dto.request.MealLogRequestDto;
import com.wellu.usermanagement.dto.response.MealLogResponseDto;
import com.wellu.usermanagement.entity.MealLog;
import com.wellu.usermanagement.entity.MealLogEntry;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.exception.ResourceNotFoundException;
import com.wellu.usermanagement.exception.UserNotFoundException;
import com.wellu.usermanagement.mapper.MealLogEntryMapper;
import com.wellu.usermanagement.mapper.MealLogMapper;
import com.wellu.usermanagement.repository.MealEntryRepository;
import com.wellu.usermanagement.repository.MealLogRepository;
import com.wellu.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MealLogService {

    private final MealLogRepository mealLogRepository;
    private final MealEntryRepository mealEntryRepository;
    private final MealLogMapper mealLogMapper;
    private final MealLogEntryMapper mealLogEntryMapper;
    private final UserRepository userRepository;

    public List<MealLogResponseDto> getLogs(UUID userId) {
        return mealLogRepository.findByUser_Id(userId)
                .stream()
                .map(mealLogMapper::toDto)
                .toList();
    }

    public MealLogResponseDto getLog(UUID logId, UUID userId) {
        MealLog log = mealLogRepository
                .findByIdAndUser_Id(logId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal log not found"));

        return mealLogMapper.toDto(log);
    }

    @Transactional
    public MealLogResponseDto createLog(UUID userId, MealLogRequestDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        MealLog log = mealLogMapper.toEntity(dto);
        log.setUser(user);

        MealLog saved = mealLogRepository.save(log);

        return mealLogMapper.toDto(saved);
    }

    @Transactional
    public MealLogResponseDto addEntry(UUID logId,
                                       UUID userId,
                                       MealLogEntryRequestDto entryDto) {

        MealLog log = mealLogRepository
                .findByIdAndUser_Id(logId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal log not found"));

        MealLogEntry entry = mealLogEntryMapper.toEntity(entryDto);
        log.addEntry(entry);

        mealEntryRepository.save(entry);

        return mealLogMapper.toDto(log);
    }

    @Transactional
    public void deleteLog(UUID logId, UUID userId) {

        MealLog log = mealLogRepository
                .findByIdAndUser_Id(logId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal log not found"));

        mealLogRepository.delete(log);
    }

    @Transactional
    public void deleteEntry(UUID entryId, UUID userId) {

        MealLogEntry entry = mealEntryRepository
                .findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal log entry not found"));

        if (!entry.getMealLog().getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Meal log entry not found for this user");
        }

        MealLog log = entry.getMealLog();
        log.getEntries().remove(entry);

        mealEntryRepository.delete(entry);
    }
}
