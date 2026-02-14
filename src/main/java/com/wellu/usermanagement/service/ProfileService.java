package com.wellu.usermanagement.service;


import com.wellu.usermanagement.dto.response.UserProfileResponse;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.entity.UserProfile;
import com.wellu.usermanagement.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserProfileResponse> getProfile() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UUID userId = UUID.fromString(authentication.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = user.getProfile(); // should NEVER be null now

        UserProfileResponse response =
                new UserProfileResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        profile.getAge(),
                        profile.getWeight(),
                        profile.getHeight(),
                        profile.calculateBMI()
                );

        return ResponseEntity.ok(response);
    }
}
