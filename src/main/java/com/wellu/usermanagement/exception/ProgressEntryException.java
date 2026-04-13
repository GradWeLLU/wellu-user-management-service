package com.wellu.usermanagement.exception;

import org.wellu.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProgressEntryException extends BaseException {
    public ProgressEntryException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "PROGRESS_ENTRY_ERROR");
    }
}
