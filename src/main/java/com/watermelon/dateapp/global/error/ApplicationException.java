package com.watermelon.dateapp.global.error;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
	private int statusCode;
	private String code;
	private final ErrorType errorType;
	private final Object data;

	public ApplicationException(ErrorType errorType, Object data, Throwable cause) {
		super(errorType.getMessage(), cause);
		this.statusCode = errorType.getStatus();
		this.code = errorType.getCode();
		this.errorType = errorType;
		this.data = data;
	}

	public ApplicationException(ErrorType errorType) {
		this(errorType, errorType.getMessage(), null);
	}
}