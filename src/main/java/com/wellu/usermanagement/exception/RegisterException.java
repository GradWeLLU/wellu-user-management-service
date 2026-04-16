package com.wellu.usermanagement.exception;

import org.wellu.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class RegisterException extends BaseException {
    public RegisterException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "REGISTER_ERROR");
    }
}
