package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.ExerciseLogEntryRequestDto;
import com.wellu.usermanagement.dto.request.ExerciseLogRequestDto;
import com.wellu.usermanagement.dto.response.ExerciseLogResponseDto;
import com.wellu.usermanagement.security.CustomUserPrincipal;
import com.wellu.usermanagement.service.ExerciseLogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/exerciseLogs")
public class ExerciseLogController {

    private final ExerciseLogService exerciseLogService;

    public ExerciseLogController(ExerciseLogService exerciseLogService) {
        this.exerciseLogService = exerciseLogService;
    }

    @PostMapping
    public ResponseEntity<ExerciseLogResponseDto> createLog(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody ExerciseLogRequestDto dto
    ) {

        UUID userId = principal.getUserId();

        ExerciseLogResponseDto response =
                exerciseLogService.createLog(userId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/addEntry/{logId}")
    public ResponseEntity<ExerciseLogResponseDto> addEntry(
            @PathVariable UUID logId,
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody ExerciseLogEntryRequestDto entryDto
    ) {

        UUID userId = principal.getUserId();

        ExerciseLogResponseDto response =
                exerciseLogService.addEntry(logId, userId, entryDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteLog(
            @PathVariable UUID logId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {

        UUID userId = principal.getUserId();

        exerciseLogService.deleteLog(logId, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/entries/{entryId}")
    public ResponseEntity<Void> deleteEntry(
            @PathVariable UUID entryId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {

        exerciseLogService.deleteEntry(entryId, principal.getUserId());
        return ResponseEntity.noContent().build();
    }

}
