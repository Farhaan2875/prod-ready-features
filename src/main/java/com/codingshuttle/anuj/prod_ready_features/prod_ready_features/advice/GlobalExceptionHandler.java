package com.codingshuttle.anuj.prod_ready_features.prod_ready_features.advice;

import com.codingshuttle.anuj.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //automatic
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) //WILL AUTOMATICALLY RUN AND HANDLE EXCEPTION OF THIS TYPE
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

    }
}
