package com.wellu.usermanagement.service;


import com.wellu.usermanagement.dto.response.UserProfileResponse;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.entity.UserProfile;
import com.wellu.usermanagement.exception.UserNotFoundException;
import com.wellu.usermanagement.mapper.UserMapper;
import com.wellu.usermanagement.mapper.UserProfileMapper;
import com.wellu.usermanagement.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final UserProfileMapper userProfileMapper;

    public ProfileService(UserRepository userRepository, UserProfileMapper userProfileMapper) {
        this.userRepository = userRepository;
        this.userProfileMapper = userProfileMapper;
    }

    public ResponseEntity<UserProfileResponse> getProfile() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UUID userId = UUID.fromString(authentication.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserProfile profile = user.getProfile(); // should NEVER be null now

        UserProfileResponse response =
                userProfileMapper.toResponse(user);

        return ResponseEntity.ok(response);
    }
}
