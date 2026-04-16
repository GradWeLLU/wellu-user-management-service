package com.wellu.usermanagement.exception;

import org.wellu.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND_ERROR");
    }
}
