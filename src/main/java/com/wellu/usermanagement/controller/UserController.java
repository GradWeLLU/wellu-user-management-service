package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.UserLoginRequest;
import com.wellu.usermanagement.dto.request.UserRegisterRequest;
import com.wellu.usermanagement.dto.response.LoginResponse;
import com.wellu.usermanagement.dto.response.UserRegisterResponse;
import com.wellu.usermanagement.repository.UserRepository;
import com.wellu.usermanagement.service.AuthService;
import com.wellu.usermanagement.service.JwtService;
import com.wellu.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthService authService;

    public UserController(UserService userService, JwtService jwtService, AuthService authService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody
    UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody UserLoginRequest request
    ) {
        String token = authService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }

}
