package com.wellu.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.wellu.common.exception.BaseException;
public class AuthenticationException extends BaseException {
    public AuthenticationException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
    }
}