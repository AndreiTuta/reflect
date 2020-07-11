package com.at.reflect.model.factory;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.at.reflect.model.response.ErrorResponse;

@Service
public class ErrorResponseFactory {

	public ErrorResponse buildError(Exception e) {
		return buildError(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ErrorResponse buildError(Exception e, HttpStatus status) {
		// TODO redo error msg
		return ErrorResponse.builder().httpStatus(status.value()).errorMessage(e.getMessage()).build();
	}

	/**
	 * @param status
	 * @param br
	 */
	public ErrorResponse throwNewError(final HttpStatus status, final BindingResult br) {
		ErrorResponse error = ErrorResponse.builder().errorMessage(br.getFieldError().getDefaultMessage())
				.field(getFieldPath(br.getFieldError().getArguments())).httpStatus(status.value()).build();
		return error;
	}

	private String getFieldPath(Object[] fieldArguments) {
		// TODO redo as a util
		DefaultMessageSourceResolvable source = (DefaultMessageSourceResolvable) fieldArguments[0];
		return source.getCodes()[0];
	}

}
