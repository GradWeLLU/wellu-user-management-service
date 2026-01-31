package com.wellu.usermanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="badges")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "badge_name", nullable = false)
    private String name;

    @Column(name = "badge_description", nullable = false)
    private String description;

    @Column(name = "badge_image_URL", nullable = false)
    private String imageURL;

    @Column(name = "earned_at",nullable = false)
    private Instant earnedAt;

    @ManyToOne
    @JoinColumn(name = "user_profile_id",nullable = false)
    private UserProfile userProfile;



}
