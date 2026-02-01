package com.wellu.usermanagement.controller;

import com.wellu.usermanagement.dto.request.UserRegisterRequest;
import com.wellu.usermanagement.dto.response.UserRegisterResponse;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody
    UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);

    }

}
