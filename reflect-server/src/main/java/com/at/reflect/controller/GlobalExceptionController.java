package com.at.reflect.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.at.reflect.model.factory.ErrorResponseFactory;
import com.at.reflect.model.response.ErrorResponse;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {

	private final ErrorResponseFactory errorResponseFactory;

	/**
	 * 
	 * Example:
	 * 
	 * <pre>
	{
	"httpStatus": 400,
	"field": "songRequest.title",
	"errorMessage": "Title must not be null/blank"
	}
	 * </pre>
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> unHandledExceptions(MethodArgumentNotValidException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(errorResponseFactory.throwNewError(HttpStatus.BAD_REQUEST, e.getBindingResult()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> unHandledExceptions(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseFactory.buildError(e));
	}

}
