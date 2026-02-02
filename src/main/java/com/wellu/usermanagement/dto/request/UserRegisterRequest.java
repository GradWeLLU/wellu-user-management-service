package com.wellu.usermanagement.dto.request;

import com.wellu.usermanagement.enumeration.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

public record UserRegisterRequest (
    String firstName,
    String lastName,
    @Email String email,
    String country,
    UserRole role,
    String password
){}
