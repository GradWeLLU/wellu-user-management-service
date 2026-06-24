package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.AllergyDto;
import com.wellu.usermanagement.security.CustomUserPrincipal;
import com.wellu.usermanagement.service.AllergyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/allergies")
public class AllergyController {

    private final AllergyService allergyService;

    public AllergyController(AllergyService allergyService) {
        this.allergyService = allergyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AllergyDto>> getAllAllergies(
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(allergyService.getAllergies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllergyDto> getAllergyById(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID id) {
        return ResponseEntity.ok(allergyService.getAllergyById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<AllergyDto> createAllergy(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody AllergyDto dto) {
        AllergyDto created = allergyService.createAllergy(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AllergyDto> updateAllergy(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID id,
            @RequestBody AllergyDto dto) {
        return ResponseEntity.ok(allergyService.updateAllergy(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAllergy(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID id) {
        allergyService.deleteAllergy(id);
        return ResponseEntity.noContent().build();
    }
}