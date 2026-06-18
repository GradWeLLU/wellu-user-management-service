package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.MealLogEntryRequestDto;
import com.wellu.usermanagement.dto.request.MealLogRequestDto;
import com.wellu.usermanagement.dto.response.MealLogResponseDto;
import com.wellu.usermanagement.security.CustomUserPrincipal;
import com.wellu.usermanagement.service.MealLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mealLogs")
public class MealLogController {

    private final MealLogService mealLogService;

    public MealLogController(MealLogService mealLogService) {
        this.mealLogService = mealLogService;
    }

    @GetMapping
    public ResponseEntity<List<MealLogResponseDto>> getLogs(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return ResponseEntity.ok(mealLogService.getLogs(principal.getUserId()));
    }

    @GetMapping("/{logId}")
    public ResponseEntity<MealLogResponseDto> getLog(
            @PathVariable UUID logId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        MealLogResponseDto response = mealLogService.getLog(logId, principal.getUserId());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MealLogResponseDto> createLog(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody MealLogRequestDto dto
    ) {

        UUID userId = principal.getUserId();

        MealLogResponseDto response = mealLogService.createLog(userId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/addEntry/{logId}")
    public ResponseEntity<MealLogResponseDto> addEntry(
            @PathVariable UUID logId,
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody MealLogEntryRequestDto entryDto
    ) {

        UUID userId = principal.getUserId();

        MealLogResponseDto response = mealLogService.addEntry(logId, userId, entryDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteLog(
            @PathVariable UUID logId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {

        UUID userId = principal.getUserId();

        mealLogService.deleteLog(logId, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/entries/{entryId}")
    public ResponseEntity<Void> deleteEntry(
            @PathVariable UUID entryId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {

        mealLogService.deleteEntry(entryId, principal.getUserId());
        return ResponseEntity.noContent().build();
    }
}
