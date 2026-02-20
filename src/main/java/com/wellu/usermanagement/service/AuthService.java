package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.UserLoginRequest;
import com.wellu.usermanagement.dto.response.LoginResponse;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.exception.AuthenticationException;
import com.wellu.usermanagement.repository.UserRepository;
import com.wellu.usermanagement.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<LoginResponse> login (UserLoginRequest userLoginRequest){
        String userEmail=userLoginRequest.email();
        String password=userLoginRequest.password();

        User user =userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new AuthenticationException("Invalid email or password")
                );
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new AuthenticationException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.getId(), user.getEmail());

        LoginResponse loginResponse= new LoginResponse("user logged in successfully",token);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}
