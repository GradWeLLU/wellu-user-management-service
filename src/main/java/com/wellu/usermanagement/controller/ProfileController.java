package com.wellu.usermanagement.controller;


import com.wellu.usermanagement.dto.request.UserProfileUpdateRequest;
import com.wellu.usermanagement.dto.response.UserProfileResponse;
import com.wellu.usermanagement.service.ProfileService;
import com.wellu.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;

    public ProfileController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }


    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile() {
        return profileService.getProfile();
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @RequestBody UserProfileUpdateRequest request
    ) {
        System.out.println("here"); // now will print
        return profileService.updateProfile(request);
    }


}
