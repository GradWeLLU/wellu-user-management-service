package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.UserLoginRequest;
import com.wellu.usermanagement.dto.request.UserRegisterRequest;
import com.wellu.usermanagement.dto.response.LoginResponse;
import com.wellu.usermanagement.dto.response.NutritionPlanRequestDTO;
import com.wellu.usermanagement.dto.response.UserRegisterResponse;
import com.wellu.usermanagement.dto.response.WorkoutPlanRequestDTO;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.security.CustomUserPrincipal;
import com.wellu.usermanagement.service.AuthService;
import com.wellu.usermanagement.security.JwtService;
import com.wellu.usermanagement.service.PlanGenerationService;
import com.wellu.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthService authService;
    private final PlanGenerationService planGenerationService;


    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody
    UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody UserLoginRequest request
    ) {
//        String token = authService.login(request);
        return authService.login(request);
    }
    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/request-workout-details")
    public ResponseEntity<WorkoutPlanRequestDTO> getWorkoutPlanDetails(@AuthenticationPrincipal CustomUserPrincipal principal){
        UUID userId = principal.getUserId();
        return planGenerationService.buildWorkoutPlanRequestDTO(userId);
    }

    @GetMapping("/request-nutrition-details")
    public ResponseEntity<NutritionPlanRequestDTO> getNutritionPlanDetails(@AuthenticationPrincipal CustomUserPrincipal principal) {
        UUID userId = principal.getUserId();
        return planGenerationService.buildNutritionPlanRequestDTO(userId);
    }

}
