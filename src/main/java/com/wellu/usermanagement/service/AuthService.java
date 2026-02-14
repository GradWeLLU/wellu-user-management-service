package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.UserLoginRequest;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.repository.UserRepository;
import com.wellu.usermanagement.security.JwtService;
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

    public String login (UserLoginRequest userLoginRequest){
        String userEmail=userLoginRequest.email();
        String password=userLoginRequest.password();

        User user =userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Wrong password");
        }
        return jwtService.generateToken(user.getId(),user.getEmail());



    }

}
