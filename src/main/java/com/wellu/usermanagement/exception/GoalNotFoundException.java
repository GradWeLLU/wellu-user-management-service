package com.wellu.usermanagement.exception;

import org.wellu.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class GoalNotFoundException extends BaseException {
    public GoalNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "GOAL_NOT_FOUND");
    }
}
