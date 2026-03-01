package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.ProgressEntryCreateRequest;
import com.wellu.usermanagement.dto.response.ProgressEntryResponse;
import com.wellu.usermanagement.security.CustomUserPrincipal;
import com.wellu.usermanagement.service.ProgressEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress-entries")
public class ProgressEntryController {

    private ProgressEntryService progressEntryService;

    public ProgressEntryController(ProgressEntryService progressEntryService) {
        this.progressEntryService = progressEntryService;
    }

    @PostMapping
    public ResponseEntity<ProgressEntryResponse> create(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestBody ProgressEntryCreateRequest request
    ) {
        return ResponseEntity.ok(progressEntryService.create(principal.getUserId(), request));
    }

    @GetMapping
    public ResponseEntity<List<ProgressEntryResponse>> getAll(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return ResponseEntity.ok(progressEntryService.getAll(principal.getUserId()));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(
//            @AuthenticationPrincipal CustomUserPrincipal principal,
//            @PathVariable UUID id
//    ) {
//        progressEntryService.delete(principal.getUserId(), id);
//        return ResponseEntity.noContent().build();
//    }
}
