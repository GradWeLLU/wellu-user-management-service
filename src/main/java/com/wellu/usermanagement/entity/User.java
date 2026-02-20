package com.wellu.usermanagement.entity;

import com.wellu.usermanagement.enumeration.UserRole;
import com.wellu.usermanagement.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
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

    @Column
    private Instant lastLogin;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, orphanRemoval = true)
    @JoinColumn(
            name = "profile_id",
            unique = true
    )
    private UserProfile profile;

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

    public void setProfile(UserProfile profile){
        this.profile = profile;
        if(profile != null){
            profile.setUser(this);
        }
    }

}
