package com.wellu.usermanagement.exception;

import org.wellu.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidGoalDateException extends BaseException {

    public InvalidGoalDateException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "INVALID_GOAL_DATE");
    }
}