package com.wellu.usermanagement.exception;

import com.wellu.usermanagement.enumeration.GoalType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;



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
    public ResponseEntity<ApiError> handleInvalidInput(HttpMessageNotReadableException ex) {

        Throwable cause = ex.getCause();

        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException ife) {

            Class<?> targetType = ife.getTargetType();

            if (targetType.equals(GoalType.class)) {
                return ResponseEntity.badRequest().body(
                        new ApiError(400, "Invalid goal type.")
                );
            }

            if (targetType.equals(java.time.LocalDate.class)) {
                return ResponseEntity.badRequest().body(
                        new ApiError(400, "Invalid date format. Use yyyy-MM-dd.")
                );
            }

            if (targetType.equals(Double.class)) {
                return ResponseEntity.badRequest().body(
                        new ApiError(400, "Invalid number format.")
                );
            }
        }

        return ResponseEntity.badRequest().body(
                new ApiError(400, "Malformed request body.")
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex) {

        if (ex.getRootCause() != null &&
                ex.getRootCause().getMessage().contains("goal_no_overlap")) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiError(409,"An active goal of this type already overlaps the selected date range."));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(400,"Database constraint violation"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex) {

        return new ResponseEntity<>(
                new ApiError(500, "Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex){
        return new ResponseEntity<>(
                new ApiError(404, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}