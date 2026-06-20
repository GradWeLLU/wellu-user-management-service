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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealLogServiceTest {

    @Mock private MealLogRepository mealLogRepository;
    @Mock private MealEntryRepository mealEntryRepository;
    @Mock private MealLogMapper mealLogMapper;
    @Mock private MealLogEntryMapper mealLogEntryMapper;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private MealLogService mealLogService;

    private UUID userId;
    private UUID logId;
    private User user;
    private MealLog mealLog;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        logId = UUID.randomUUID();

        user = new User();

        mealLog = new MealLog();
        mealLog.setMealDate(LocalDate.of(2026, 6, 20));
        mealLog.setUser(user);
    }

    // ─── createLog ───────────────────────────────────────────────────────────

    @Test
    void createLog_validUser_returnsDto() {
        MealLogRequestDto dto = new MealLogRequestDto();
        dto.setMealDate(LocalDate.of(2026, 6, 20));

        MealLogResponseDto responseDto = new MealLogResponseDto();
        responseDto.setMealDate(LocalDate.of(2026, 6, 20));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mealLogMapper.toEntity(dto)).thenReturn(mealLog);
        when(mealLogRepository.save(mealLog)).thenReturn(mealLog);
        when(mealLogMapper.toDto(mealLog)).thenReturn(responseDto);

        MealLogResponseDto result = mealLogService.createLog(userId, dto);

        assertThat(result).isNotNull();
        assertThat(result.getMealDate()).isEqualTo(LocalDate.of(2026, 6, 20));
        verify(mealLogRepository).save(mealLog);
    }

    @Test
    void createLog_userNotFound_throwsUserNotFoundException() {
        MealLogRequestDto dto = new MealLogRequestDto();
        dto.setMealDate(LocalDate.of(2026, 6, 20));

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mealLogService.createLog(userId, dto))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");

        verify(mealLogRepository, never()).save(any());
    }

    // ─── getLogs ─────────────────────────────────────────────────────────────

    @Test
    void getLogs_returnsMappedList() {
        MealLogResponseDto responseDto = new MealLogResponseDto();
        responseDto.setMealDate(LocalDate.of(2026, 6, 20));

        when(mealLogRepository.findByUser_Id(userId)).thenReturn(List.of(mealLog));
        when(mealLogMapper.toDto(mealLog)).thenReturn(responseDto);

        List<MealLogResponseDto> result = mealLogService.getLogs(userId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMealDate()).isEqualTo(LocalDate.of(2026, 6, 20));
    }

    @Test
    void getLogs_noLogs_returnsEmptyList() {
        when(mealLogRepository.findByUser_Id(userId)).thenReturn(List.of());

        List<MealLogResponseDto> result = mealLogService.getLogs(userId);

        assertThat(result).isEmpty();
    }

    // ─── getLog ──────────────────────────────────────────────────────────────

    @Test
    void getLog_existingLog_returnsDto() {
        MealLogResponseDto responseDto = new MealLogResponseDto();
        responseDto.setMealDate(LocalDate.of(2026, 6, 20));

        when(mealLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.of(mealLog));
        when(mealLogMapper.toDto(mealLog)).thenReturn(responseDto);

        MealLogResponseDto result = mealLogService.getLog(logId, userId);

        assertThat(result).isNotNull();
        assertThat(result.getMealDate()).isEqualTo(LocalDate.of(2026, 6, 20));
    }

    @Test
    void getLog_notFound_throwsResourceNotFoundException() {
        when(mealLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mealLogService.getLog(logId, userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Meal log not found");
    }

    // ─── addEntry ────────────────────────────────────────────────────────────

    @Test
    void addEntry_validLog_addsEntryAndSaves() {
        MealLogEntryRequestDto entryDto = new MealLogEntryRequestDto();
        entryDto.setMealName("Grilled Chicken");
        entryDto.setMealType("LUNCH");
        entryDto.setCalories(300);

        MealLogEntry entry = new MealLogEntry();
        entry.setMealName("Grilled Chicken");

        MealLogResponseDto responseDto = new MealLogResponseDto();
        responseDto.setMealDate(LocalDate.of(2026, 6, 20));

        when(mealLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.of(mealLog));
        when(mealLogEntryMapper.toEntity(entryDto)).thenReturn(entry);
        when(mealLogRepository.save(mealLog)).thenReturn(mealLog);
        when(mealLogMapper.toDto(mealLog)).thenReturn(responseDto);

        MealLogResponseDto result = mealLogService.addEntry(logId, userId, entryDto);

        assertThat(result).isNotNull();
        assertThat(mealLog.getEntries()).contains(entry);
        verify(mealLogRepository).save(mealLog);
    }

    @Test
    void addEntry_logNotFound_throwsResourceNotFoundException() {
        MealLogEntryRequestDto entryDto = new MealLogEntryRequestDto();

        when(mealLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mealLogService.addEntry(logId, userId, entryDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Meal log not found");

        verify(mealLogRepository, never()).save(any());
    }

    // ─── deleteLog ───────────────────────────────────────────────────────────

    @Test
    void deleteLog_existingLog_deletesSuccessfully() {
        when(mealLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.of(mealLog));

        mealLogService.deleteLog(logId, userId);

        verify(mealLogRepository).delete(mealLog);
    }

    @Test
    void deleteLog_notFound_throwsResourceNotFoundException() {
        when(mealLogRepository.findByIdAndUser_Id(logId, userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mealLogService.deleteLog(logId, userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Meal log not found");

        verify(mealLogRepository, never()).delete(any());
    }

    // ─── deleteEntry ─────────────────────────────────────────────────────────

    @Test
    void deleteEntry_validEntry_deletesSuccessfully() {
        UUID entryId = UUID.randomUUID();

        User owner = mock(User.class);
        when(owner.getId()).thenReturn(userId);

        MealLog log = mock(MealLog.class);
        when(log.getUser()).thenReturn(owner);
        when(log.getEntries()).thenReturn(new ArrayList<>());

        MealLogEntry entry = new MealLogEntry();
        entry.setMealLog(log);

        when(mealEntryRepository.findById(entryId)).thenReturn(Optional.of(entry));

        mealLogService.deleteEntry(entryId, userId);

        verify(mealEntryRepository).delete(entry);
    }

    @Test
    void deleteEntry_entryNotFound_throwsResourceNotFoundException() {
        UUID entryId = UUID.randomUUID();

        when(mealEntryRepository.findById(entryId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mealLogService.deleteEntry(entryId, userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Meal log entry not found");

        verify(mealEntryRepository, never()).delete(any());
    }

    @Test
    void deleteEntry_wrongUser_throwsResourceNotFoundException() {
        UUID entryId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();

        User owner = mock(User.class);
        when(owner.getId()).thenReturn(otherUserId);

        MealLog log = mock(MealLog.class);
        when(log.getUser()).thenReturn(owner);

        MealLogEntry entry = new MealLogEntry();
        entry.setMealLog(log);

        when(mealEntryRepository.findById(entryId)).thenReturn(Optional.of(entry));

        assertThatThrownBy(() -> mealLogService.deleteEntry(entryId, userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Meal log entry not found for this user");

        verify(mealEntryRepository, never()).delete(any());
    }
}