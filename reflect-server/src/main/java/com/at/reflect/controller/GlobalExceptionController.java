package com.at.reflect.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.error.exception.PathException;
import com.at.reflect.model.factory.ErrorResponseFactory;
import com.at.reflect.model.response.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {

    private final ErrorResponseFactory errorResponseFactory;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ResponseBody
    public ErrorResponse notFound(NotFoundException e) {
        return errorResponseFactory.buildError(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PathException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ResponseBody
    public ErrorResponse pathExceptions(PathException e) {
        return errorResponseFactory.buildError(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ResponseBody
    public ErrorResponse methodArgumentvalidationException(MethodArgumentNotValidException e) {
        return errorResponseFactory.throwNewError(HttpStatus.BAD_REQUEST, e.getBindingResult());
    }

    /*
     * Use as a default exception to be thrown when the credentials are invalid TODO
     * Update to return relevant information
     */
    @Operation(hidden = true)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ResponseBody
    public ErrorResponse unHandledExceptions(Exception e) {
        return errorResponseFactory.buildError(e);
    }

//     Random exceptions that I got and should be handled asap
//    [javax.validation.UnexpectedTypeException: HV000030: No validator could be found for constraint 'javax.validation.constraints.NotBlank' validating type 'java.lang.Boolean'. Check configuration for 'available']
}
