package com.wellu.usermanagement.exception;

import org.wellu.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "USER_NOT_FOUND_ERROR");
    }
}