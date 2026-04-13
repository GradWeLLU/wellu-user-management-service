package com.wellu.usermanagement.exception;

import org.wellu.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProfileNotFoundException extends BaseException {
    public ProfileNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "PROFILE_NOT_FOUND");
    }
}
