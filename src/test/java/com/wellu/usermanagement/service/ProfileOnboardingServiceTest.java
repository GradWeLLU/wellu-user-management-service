package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.*;
import com.wellu.usermanagement.dto.response.CompleteProfileResponse;
import com.wellu.usermanagement.entity.*;
import com.wellu.usermanagement.exception.UserNotFoundException;
import com.wellu.usermanagement.repository.PreferenceRepository;
import com.wellu.usermanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileOnboardingServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PreferenceRepository preferenceRepository;

    @InjectMocks
    private ProfileOnboardingService profileOnboardingService;

    private UUID userId;
    private User user;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User();

        // Mock the security context so getCurrentUser() works
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userId.toString(), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    }

    @Test
    void completeMyProfile_newUser_createsProfileSuccessfully() {
        CompleteProfileRequest request = new CompleteProfileRequest(
                "FEMALE", 22, 150.0, 50.0,
                "BEGINNER", "FAT_LOSS", 60, "ADVANCED",
                "MORNING", "VEGETARIAN",
                new InjuryDetailsRequest(false, null, null, null, null),
                new MedicationDetailsRequest(false, null, null),
                new AllergyDetailsRequest(false, null)
        );

        CompleteProfileResponse response = profileOnboardingService.completeMyProfile(request);

        assertThat(response).isNotNull();
        assertThat(response.gender()).isEqualTo("FEMALE");
        assertThat(response.age()).isEqualTo(22);
        assertThat(response.height()).isEqualTo(150.0);
        assertThat(response.weight()).isEqualTo(50.0);
        assertThat(response.fitnessLevel()).isEqualTo("BEGINNER");
        assertThat(response.mainGoal()).isEqualTo("FAT_LOSS");
        assertThat(response.duration()).isEqualTo(60);
        assertThat(response.intensity()).isEqualTo("ADVANCED");
        assertThat(response.workoutTime()).isEqualTo("MORNING");
        assertThat(response.diet()).isEqualTo("VEGETARIAN");

        verify(userRepository).save(user);
        verify(preferenceRepository).save(any(Preference.class));
    }

    @Test
    void completeMyProfile_userNotFound_throwsUserNotFoundException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        CompleteProfileRequest request = new CompleteProfileRequest(
                "FEMALE", 22, 150.0, 50.0,
                "BEGINNER", "FAT_LOSS", 60, "ADVANCED",
                "MORNING", "VEGETARIAN",
                new InjuryDetailsRequest(false, null, null, null, null),
                new MedicationDetailsRequest(false, null, null),
                new AllergyDetailsRequest(false, null)
        );

        assertThatThrownBy(() -> profileOnboardingService.completeMyProfile(request))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void completeMyProfile_withInjury_setsInjuryCorrectly() {
        CompleteProfileRequest request = new CompleteProfileRequest(
                "FEMALE", 22, 150.0, 50.0,
                "BEGINNER", "FAT_LOSS", 60, "ADVANCED",
                "MORNING", "VEGETARIAN",
                new InjuryDetailsRequest(true, List.of("Knee", "Shoulder"), null, null, true),
                new MedicationDetailsRequest(false, null, null),
                new AllergyDetailsRequest(false, null)
        );

        CompleteProfileResponse response = profileOnboardingService.completeMyProfile(request);

        assertThat(response.injuryDetails().hasInjury()).isTrue();
        assertThat(response.injuryDetails().areas()).containsExactlyInAnyOrder("Knee", "Shoulder");
    }

    @Test
    void completeMyProfile_withMedication_setsMedicationCorrectly() {
        CompleteProfileRequest request = new CompleteProfileRequest(
                "FEMALE", 22, 150.0, 50.0,
                "BEGINNER", "FAT_LOSS", 60, "ADVANCED",
                "MORNING", "VEGETARIAN",
                new InjuryDetailsRequest(false, null, null, null, null),
                new MedicationDetailsRequest(true, "Ibuprofen", "Pain relief"),
                new AllergyDetailsRequest(false, null)
        );

        CompleteProfileResponse response = profileOnboardingService.completeMyProfile(request);

        assertThat(response.medicationDetails().takesMedication()).isTrue();
        assertThat(response.medicationDetails().medicationName()).isEqualTo("Ibuprofen");
    }

    @Test
    void completeMyProfile_withAllergies_setsAllergiesCorrectly() {
        CompleteProfileRequest request = new CompleteProfileRequest(
                "FEMALE", 22, 150.0, 50.0,
                "BEGINNER", "FAT_LOSS", 60, "ADVANCED",
                "MORNING", "VEGETARIAN",
                new InjuryDetailsRequest(false, null, null, null, null),
                new MedicationDetailsRequest(false, null, null),
                new AllergyDetailsRequest(true, "Peanuts, Gluten")
        );

        CompleteProfileResponse response = profileOnboardingService.completeMyProfile(request);

        assertThat(response.allergyDetails().hasAllergies()).isTrue();
        assertThat(response.allergyDetails().allergies()).contains("Peanuts");
        assertThat(response.allergyDetails().allergies()).contains("Gluten");
    }
}