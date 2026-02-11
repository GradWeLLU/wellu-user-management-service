package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.MedicationCreateRequestDto;
import com.wellu.usermanagement.dto.request.MedicationUpdateRequestDto;
import com.wellu.usermanagement.dto.response.MedicationResponseDto;
import com.wellu.usermanagement.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping
    public ResponseEntity<MedicationResponseDto> create(
            @Valid @RequestBody MedicationCreateRequestDto request
    ) {
        MedicationResponseDto response = medicationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(medicationService.getById(id));
    }

    @GetMapping("/health-profile/{healthProfileId}")
    public ResponseEntity<List<MedicationResponseDto>> getAllByHealthProfile(
            @PathVariable UUID healthProfileId
    ) {
        return ResponseEntity.ok(medicationService.getAllByHealthProfile(healthProfileId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody MedicationUpdateRequestDto request
    ) {
        return ResponseEntity.ok(medicationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        medicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}