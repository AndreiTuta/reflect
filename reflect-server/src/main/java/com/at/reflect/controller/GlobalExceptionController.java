package com.at.reflect.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.error.exception.PathException;
import com.at.reflect.model.factory.ErrorResponseFactory;
import com.at.reflect.model.response.ErrorResponse;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {

    private final ErrorResponseFactory errorResponseFactory;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(errorResponseFactory.buildError(e, HttpStatus.NOT_FOUND));
    }
    
    @ExceptionHandler(PathException.class)
    public ResponseEntity<ErrorResponse> pathExceptions(PathException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(errorResponseFactory.buildError(e, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentvalidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(errorResponseFactory.throwNewError(HttpStatus.BAD_REQUEST, e.getBindingResult()));
    }

    /*
     * Use as a default exception to be thrown when the credentials are invalid TODO
     * Update to return relevant information
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unHandledExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseFactory.buildError(e));
    }

}
