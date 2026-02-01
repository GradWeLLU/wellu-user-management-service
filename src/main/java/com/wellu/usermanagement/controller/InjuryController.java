package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.InjuryRequestDto;
import com.wellu.usermanagement.dto.response.InjuryResponseDto;
import com.wellu.usermanagement.entity.Injury;
import com.wellu.usermanagement.enumeration.SeverityLevel;
import com.wellu.usermanagement.service.InjuryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/injuries")
@RequiredArgsConstructor
public class InjuryController {
    private final InjuryService injuryService;


    //Create a new Injury
    @PostMapping
    public ResponseEntity<InjuryResponseDto> createInjury(@Valid @RequestBody InjuryRequestDto injury){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(injuryService.createInjury(injury));
    }

    //Get all injuries for a specific health profile
    @GetMapping("/profile/{healthProfileId}")
    public ResponseEntity<List<Injury>> getByHealthProfile(@PathVariable UUID healthProfileId){
        List<Injury> injuries = injuryService.getByHealthProfile(healthProfileId);
        return ResponseEntity.ok(injuries);
    }
    @GetMapping
    public ResponseEntity<List<Injury>> getAllInjuries(){
        return ResponseEntity.ok(injuryService.getAllInjuries());
    }
    // Update severity level for a specific injury
    @PatchMapping("/{injuryId}/severity")
    public ResponseEntity<Injury> updateSeverity(@PathVariable UUID injuryId,
                                                 @RequestParam SeverityLevel level) {
        Injury updated = injuryService.updateSeverity(injuryId, level);
        return ResponseEntity.ok(updated);
    }
}
