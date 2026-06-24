package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.CreateInjuryRequest;
import com.wellu.usermanagement.dto.response.InjuryResponseDto;
import com.wellu.usermanagement.entity.Injury;
import com.wellu.usermanagement.enumeration.SeverityLevel;
import com.wellu.usermanagement.security.CustomUserPrincipal;
import com.wellu.usermanagement.service.InjuryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/injuries")
public class InjuryController {

    private final InjuryService injuryService;

    public InjuryController(InjuryService injuryService) {
        this.injuryService = injuryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Injury>> getAllInjuries(
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(injuryService.getAllInjuries());
    }

    @GetMapping("/byHealthProfile/{healthProfileId}")
    public ResponseEntity<List<Injury>> getByHealthProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID healthProfileId) {
        return ResponseEntity.ok(injuryService.getByHealthProfile(healthProfileId));
    }

    @PostMapping("/create")
    public ResponseEntity<InjuryResponseDto> createInjury(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody CreateInjuryRequest dto) {
        InjuryResponseDto created = injuryService.createInjury(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/updateSeverity/{injuryId}")
    public ResponseEntity<Injury> updateSeverity(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID injuryId,
            @RequestParam SeverityLevel severityLevel) {
        return ResponseEntity.ok(injuryService.updateSeverity(injuryId, severityLevel));
    }
}