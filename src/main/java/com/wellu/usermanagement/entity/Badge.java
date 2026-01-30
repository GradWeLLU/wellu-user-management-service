package com.wellu.usermanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="bagde")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id", nullable = false)
    private Long id;

    @Column(name = "badge_name", nullable = false)
    private String name;

    @Column(name = "badge_description", nullable = false)
    private String description;

    @Column(name = "badge_imageURL", nullable = false)
    private String imageURL;

    @Column(name = "date_earned",nullable = false)
    private LocalDate dateEarned;

    @ManyToOne
    @JoinColumn(name = "id",nullable = false)
    private UserProfile userProfile;



}
