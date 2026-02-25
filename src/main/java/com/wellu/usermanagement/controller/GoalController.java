package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.GoalRequest;
import com.wellu.usermanagement.dto.response.GoalResponse;
import com.wellu.usermanagement.security.CustomUserPrincipal;
import com.wellu.usermanagement.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/goals")
//@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("/allGoals")
    public ResponseEntity<List<GoalResponse>> getGoals(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return ResponseEntity.ok(
                goalService.getGoals(principal.getUserId())
        );
    }

    @PostMapping("/createGoal")
    public ResponseEntity<GoalResponse> createGoal(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody GoalRequest request
    ) {
        UUID userId = principal.getUserId();
        GoalResponse response = goalService.createGoal(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
