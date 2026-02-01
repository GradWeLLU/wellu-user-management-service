package com.wellu.usermanagement.dto.request;

import com.wellu.usermanagement.entity.UserProfile;
import com.wellu.usermanagement.enumeration.UserRole;
import com.wellu.usermanagement.enumeration.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@ToString
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private UserRole role;
    private String password;


}
