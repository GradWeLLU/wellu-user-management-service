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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseLogServiceTest {

    @Mock private ExerciseLogRepository exerciseLogRepository;
    @Mock private ExerciseEntryRepository exerciseEntryRepository;
    @Mock private ExerciseLogMapper exerciseLogMapper;
    @Mock private ExerciseLogEntryMapper exerciseLogEntryMapper;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private ExerciseLogService exerciseLogService;

    private UUID userId;
    private UUID logId;
    private User user;
    private ExerciseLog exerciseLog;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        logId = UUID.randomUUID();

        user = new User();

        exerciseLog = new ExerciseLog();
        exerciseLog.setWorkoutDate(LocalDate.of(2026, 6, 20));
        exerciseLog.setUser(user);
    }

    // ─── createLog ───────────────────────────────────────────────────────────

    @Test
    void createLog_validUser_returnsDto() {
        ExerciseLogRequestDto dto = new ExerciseLogRequestDto();
        dto.setWorkoutDate(LocalDate.of(2026, 6, 20));

        ExerciseLogResponseDto responseDto = new ExerciseLogResponseDto();
        responseDto.setWorkoutDate(LocalDate.of(2026, 6, 20));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(exerciseLogMapper.toEntity(dto)).thenReturn(exerciseLog);
        when(exerciseLogRepository.save(exerciseLog)).thenReturn(exerciseLog);
        when(exerciseLogMapper.toDto(exerciseLog)).thenReturn(responseDto);

        ExerciseLogResponseDto result = exerciseLogService.createLog(userId, dto);

        assertThat(result).isNotNull();
        assertThat(result.getWorkoutDate()).isEqualTo(LocalDate.of(2026, 6, 20));
        verify(exerciseLogRepository).save(exerciseLog);
    }

    @Test
    void createLog_userNotFound_throwsUserNotFoundException() {
        ExerciseLogRequestDto dto = new ExerciseLogRequestDto();
        dto.setWorkoutDate(LocalDate.of(2026, 6, 20));

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseLogService.createLog(userId, dto))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");

        verify(exerciseLogRepository, never()).save(any());
    }

    // ─── getLogs ─────────────────────────────────────────────────────────────

    @Test
    void getLogs_returnsMappedList() {
        ExerciseLogResponseDto responseDto = new ExerciseLogResponseDto();
        responseDto.setWorkoutDate(LocalDate.of(2026, 6, 20));

        when(exerciseLogRepository.findByUser_Id(userId)).thenReturn(List.of(exerciseLog));
        when(exerciseLogMapper.toDto(exerciseLog)).thenReturn(responseDto);

        List<ExerciseLogResponseDto> result = exerciseLogService.getLogs(userId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getWorkoutDate()).isEqualTo(LocalDate.of(2026, 6, 20));
    }

    @Test
    void getLogs_noLogs_returnsEmptyList() {
        when(exerciseLogRepository.findByUser_Id(userId)).thenReturn(List.of());

        List<ExerciseLogResponseDto> result = exerciseLogService.getLogs(userId);

        assertThat(result).isEmpty();
    }

    // ─── getLog ──────────────────────────────────────────────────────────────

    @Test
    void getLog_existingLog_returnsDto() {
        ExerciseLogResponseDto responseDto = new ExerciseLogResponseDto();
        responseDto.setWorkoutDate(LocalDate.of(2026, 6, 20));

        when(exerciseLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.of(exerciseLog));
        when(exerciseLogMapper.toDto(exerciseLog)).thenReturn(responseDto);

        ExerciseLogResponseDto result = exerciseLogService.getLog(logId, userId);

        assertThat(result).isNotNull();
        assertThat(result.getWorkoutDate()).isEqualTo(LocalDate.of(2026, 6, 20));
    }

    @Test
    void getLog_notFound_throwsResourceNotFoundException() {
        when(exerciseLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseLogService.getLog(logId, userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Exercise log not found");
    }

    // ─── addEntry ────────────────────────────────────────────────────────────

    @Test
    void addEntry_validLog_addsEntryAndSaves() {
        ExerciseLogEntryRequestDto entryDto = new ExerciseLogEntryRequestDto();
        entryDto.setExerciseName("Bench Press");

        ExerciseLogEntry entry = new ExerciseLogEntry();
        entry.setExerciseName("Bench Press");

        ExerciseLogResponseDto responseDto = new ExerciseLogResponseDto();
        responseDto.setWorkoutDate(LocalDate.of(2026, 6, 20));

        when(exerciseLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.of(exerciseLog));
        when(exerciseLogEntryMapper.toEntity(entryDto)).thenReturn(entry);
        when(exerciseLogRepository.save(exerciseLog)).thenReturn(exerciseLog);
        when(exerciseLogMapper.toDto(exerciseLog)).thenReturn(responseDto);

        ExerciseLogResponseDto result = exerciseLogService.addEntry(logId, userId, entryDto);

        assertThat(result).isNotNull();
        assertThat(exerciseLog.getEntries()).contains(entry);
        verify(exerciseLogRepository).save(exerciseLog);
    }

    @Test
    void addEntry_logNotFound_throwsResourceNotFoundException() {
        ExerciseLogEntryRequestDto entryDto = new ExerciseLogEntryRequestDto();

        when(exerciseLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseLogService.addEntry(logId, userId, entryDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Exercise log not found");

        verify(exerciseLogRepository, never()).save(any());
    }

    // ─── deleteLog ───────────────────────────────────────────────────────────

    @Test
    void deleteLog_existingLog_deletesSuccessfully() {
        when(exerciseLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.of(exerciseLog));

        exerciseLogService.deleteLog(logId, userId);

        verify(exerciseLogRepository).delete(exerciseLog);
    }

    @Test
    void deleteLog_notFound_throwsResourceNotFoundException() {
        when(exerciseLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseLogService.deleteLog(logId, userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Exercise log not found");

        verify(exerciseLogRepository, never()).delete(any());
    }

    // ─── deleteEntry ─────────────────────────────────────────────────────────

    @Test
    void deleteEntry_validEntry_deletesSuccessfully() {
        UUID entryId = UUID.randomUUID();

        User entryUser = new User();
        // Use reflection-friendly approach: set the ID via a known UUID
        UUID ownerId = userId;

        // Build a user whose getId() returns the same userId
        User owner = mock(User.class);
        when(owner.getId()).thenReturn(ownerId);

        ExerciseLog log = mock(ExerciseLog.class);
        when(log.getUser()).thenReturn(owner);
        when(log.getEntries()).thenReturn(new java.util.ArrayList<>());

        ExerciseLogEntry entry = new ExerciseLogEntry();
        entry.setExerciseLog(log);

        when(exerciseEntryRepository.findById(entryId)).thenReturn(Optional.of(entry));

        exerciseLogService.deleteEntry(entryId, ownerId);

        verify(exerciseEntryRepository).delete(entry);
    }

    @Test
    void deleteEntry_entryNotFound_throwsResourceNotFoundException() {
        UUID entryId = UUID.randomUUID();

        when(exerciseEntryRepository.findById(entryId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseLogService.deleteEntry(entryId, userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Exercise log entry not found");

        verify(exerciseEntryRepository, never()).delete(any());
    }

    @Test
    void deleteEntry_wrongUser_throwsResourceNotFoundException() {
        UUID entryId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();

        User owner = mock(User.class);
        when(owner.getId()).thenReturn(otherUserId);

        ExerciseLog log = mock(ExerciseLog.class);
        when(log.getUser()).thenReturn(owner);

        ExerciseLogEntry entry = new ExerciseLogEntry();
        entry.setExerciseLog(log);

        when(exerciseEntryRepository.findById(entryId)).thenReturn(Optional.of(entry));

        assertThatThrownBy(() -> exerciseLogService.deleteEntry(entryId, userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Exercise log entry not found for this user");

        verify(exerciseEntryRepository, never()).delete(any());
    }
}