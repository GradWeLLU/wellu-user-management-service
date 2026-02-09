package com.wellu.usermanagement.controller;


import com.wellu.usermanagement.dto.response.HealthProfileResponseDto;
import com.wellu.usermanagement.entity.HealthProfile;
import com.wellu.usermanagement.service.HealthProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/health-profiles")
@RequiredArgsConstructor
public class HealthProfileController {


    private final HealthProfileService healthProfileService;


    @GetMapping("/{id}")
    public ResponseEntity<HealthProfileResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(healthProfileService.getById(id));
    }


    @GetMapping
    public ResponseEntity<List<HealthProfileResponseDto>> getAll() {
        return ResponseEntity.ok(healthProfileService.getAll());
    }


    @PostMapping
    public ResponseEntity<HealthProfileResponseDto> create(@Valid @RequestBody HealthProfile profile) {
        HealthProfileResponseDto response = healthProfileService.create(profile);
        return ResponseEntity.created(URI.create("/api/health-profiles/" + response.id())).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<HealthProfileResponseDto> update(@PathVariable UUID id,
                                                           @Valid @RequestBody HealthProfile profile) {
        return ResponseEntity.ok(healthProfileService.update(id, profile));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        healthProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}