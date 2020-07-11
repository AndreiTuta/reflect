package com.at.reflect.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.at.reflect.model.factory.ErrorResponseFactory;
import com.at.reflect.model.response.ErrorResponse;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {

	private final ErrorResponseFactory errorResponseFactory;

	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<ErrorResponse> unHandledExceptions(MethodArgumentNotValidException e) {
//		return ResponseEntity.status(HttpStatus.NO_CONTENT)
//				.body(errorResponseFactory.throwNewError(HttpStatus.NO_CONTENT, e.getBindingResult()));
//	}

	/*
	 * Use as a default exception to be thrown when the credentials are invalid
	 * TODO Update to return relevant information
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> unHandledExceptions(Exception e) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponseFactory.buildError(e));
	}

}
