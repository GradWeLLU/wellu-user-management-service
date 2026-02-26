package com.wellu.usermanagement.controller;


import com.wellu.usermanagement.dto.request.CreateInjuryRequest;
import com.wellu.usermanagement.dto.request.HealthProfilePatchRequest;
import com.wellu.usermanagement.dto.request.HealthProfileUpdateRequest;
import com.wellu.usermanagement.dto.request.UserProfileUpdateRequest;
import com.wellu.usermanagement.dto.response.HealthProfileResponseDto;
import com.wellu.usermanagement.dto.response.UserProfileResponse;
import com.wellu.usermanagement.service.HealthProfileService;
import com.wellu.usermanagement.service.ProfileService;
import com.wellu.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;
    private final HealthProfileService healthProfileService;

    public ProfileController(UserService userService, ProfileService profileService, HealthProfileService healthProfileService) {
        this.userService = userService;
        this.profileService = profileService;
        this.healthProfileService = healthProfileService;
    }


    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile() {
        return profileService.getProfile();
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @RequestBody UserProfileUpdateRequest request
    ) {
        return profileService.updateProfile(request);
    }
    @GetMapping("/me/health")
    public ResponseEntity<HealthProfileResponseDto> getMyHealthProfile() {
        return ResponseEntity.ok(healthProfileService.getMyProfile());
    }

    @PutMapping("/me/health")
    public ResponseEntity<HealthProfileResponseDto> updateMyHealthProfile(
            @RequestBody HealthProfileUpdateRequest request
    ) {
        return ResponseEntity.ok(healthProfileService.updateMyProfile(request));
    }

    @PatchMapping("/me/health")
    public ResponseEntity<HealthProfileResponseDto> updateMyHealthProfile(
            @RequestBody HealthProfilePatchRequest request
            ){
        return ResponseEntity.ok(healthProfileService.patchMyProfile(request));
    }


}
