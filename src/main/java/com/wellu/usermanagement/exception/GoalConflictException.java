package com.wellu.usermanagement.exception;
import org.springframework.http.HttpStatus;
import org.wellu.common.exception.BaseException;

public class GoalConflictException extends  BaseException {
    public GoalConflictException(String message) {
        super(message, HttpStatus.CONFLICT, "INVALID_GOAL_DATE");
    }
}
