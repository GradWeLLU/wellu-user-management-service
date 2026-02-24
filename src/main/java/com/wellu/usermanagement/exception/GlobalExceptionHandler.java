package com.wellu.usermanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<ApiError> handleRegisterException(RegisterException ex) {

        return new ResponseEntity<>(
                new ApiError(400, ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {

        return new ResponseEntity<>(
                new ApiError(404, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(GoalConflictException.class)
    public ResponseEntity<ApiError> handleGoalConflictException(GoalConflictException ex) {

        return new ResponseEntity<>(
                new ApiError(409, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(InvalidGoalDateException.class)
    public ResponseEntity<ApiError> handleInvalidGoalDateException(InvalidGoalDateException ex) {

        return new ResponseEntity<>(
                new ApiError(400, ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ApiError> handleProfileNotFound(ProfileNotFoundException ex) {

        return new ResponseEntity<>(
                new ApiError(404, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthException(AuthenticationException ex) {

        return new ResponseEntity<>(
                new ApiError(401, ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidDate(HttpMessageNotReadableException ex) {

        return new ResponseEntity<>(
                new ApiError(400, "Invalid date format. Use yyyy-MM-dd"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex) {

        return new ResponseEntity<>(
                new ApiError(500, "Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}