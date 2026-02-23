package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.AllergyDto;
import com.wellu.usermanagement.service.AllergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/allergies")
@RequiredArgsConstructor
public class AllergyController {

    private final AllergyService allergyService;

    @PostMapping
    public ResponseEntity<AllergyDto> create(@RequestBody AllergyDto dto) {
        return ResponseEntity.ok(allergyService.createAllergy(dto));
    }

    @GetMapping
    public ResponseEntity<List<AllergyDto>> getAll() {
        return ResponseEntity.ok(allergyService.getAllergies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllergyDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(allergyService.getAllergyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AllergyDto> update(@PathVariable UUID id, @RequestBody AllergyDto dto) {
        return ResponseEntity.ok(allergyService.updateAllergy(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        allergyService.deleteAllergy(id);
        return ResponseEntity.noContent().build();
    }
}