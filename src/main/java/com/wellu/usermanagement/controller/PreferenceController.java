package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.PreferenceDto;
import com.wellu.usermanagement.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @PostMapping
    public ResponseEntity<PreferenceDto> create(@RequestBody PreferenceDto dto) {
        return ResponseEntity.ok(preferenceService.createPreference(dto));
    }

    @GetMapping
    public ResponseEntity<List<PreferenceDto>> getAll() {
        return ResponseEntity.ok(preferenceService.getPreferences());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferenceDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(preferenceService.getPreferenceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreferenceDto> update(@PathVariable UUID id, @RequestBody PreferenceDto dto) {
        return ResponseEntity.ok(preferenceService.updatePreference(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        preferenceService.deletePreference(id);
        return ResponseEntity.noContent().build();
    }
}