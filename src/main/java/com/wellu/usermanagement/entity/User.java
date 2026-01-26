package com.wellu.usermanagement.entity;

import com.wellu.usermanagement.enumeration.UserRole;
import com.wellu.usermanagement.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @Column(nullable = false)
    private boolean isVerified;

    //Use enums
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    //Use Bcrypt
    @Column(nullable = false)
    private String password;

    //Use enums
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false, unique = true, length = 100)
    @Email
    private String email;

    @Column(nullable = false)
    private Instant lastLogin;

    // need a profile class
    //private String profile

    @PrePersist
    protected void onCreate(){
        this.createdAt = Instant.now();
        this.status = UserStatus.ACTIVE;
        this.isVerified = false;
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = Instant.now();
    }

}
