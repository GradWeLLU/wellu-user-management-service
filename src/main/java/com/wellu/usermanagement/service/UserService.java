package com.wellu.usermanagement.service;


import com.wellu.usermanagement.dto.request.UserRegisterRequest;
import com.wellu.usermanagement.dto.response.UserRegisterResponse;
import com.wellu.usermanagement.entity.User;
import com.wellu.usermanagement.exception.RegisterException;
import com.wellu.usermanagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserRegisterResponse> register(UserRegisterRequest userRegisterRequest) {
        String userEmail=userRegisterRequest.getEmail();
        validateEmailNotTaken(userEmail);
        User user = new User();
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmail(userEmail);
        user.setPassword(
                passwordEncoder.encode(userRegisterRequest.getPassword())
        );
        user.setCountry(userRegisterRequest.getCountry());
        user.setRole(userRegisterRequest.getRole());


        userRepository.save(user);

        UserRegisterResponse response =
                new UserRegisterResponse("User registered successfully");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



    private void validateEmailNotTaken(String userEmail) {
        boolean isEmailExsist=userRepository.existsByEmail(userEmail);
        if(isEmailExsist){
            throw new RegisterException("Email already exists");
        }
    }
}


