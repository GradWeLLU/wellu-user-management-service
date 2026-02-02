package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.UserRegisterRequest;
import com.wellu.usermanagement.dto.response.UserRegisterResponse;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.exception.RegisterException;
import com.wellu.usermanagement.mapper.UserMapper;
import com.wellu.usermanagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public ResponseEntity<UserRegisterResponse> register(UserRegisterRequest userRegisterRequest) {
        try{
            String userEmail=userRegisterRequest.email();
            validateEmailNotTaken(userEmail);
            saveUser(userRegisterRequest);

            UserRegisterResponse response =
                    new UserRegisterResponse("User registered successfully");

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        catch (Exception e){
            throw new RegisterException("Unexpected error occurred");

        }
    }

    private void saveUser(UserRegisterRequest userRegisterRequest) {
        User user = userMapper.toUserEntity(userRegisterRequest);
        user.setPassword(passwordEncoder.encode(userRegisterRequest.password()));
        userRepository.save(user);
    }

    private void validateEmailNotTaken(String userEmail) {
        boolean isEmailExist=userRepository.existsByEmail(userEmail);
        if(isEmailExist){
            throw new RegisterException("Email already exists");
        }
    }
}


