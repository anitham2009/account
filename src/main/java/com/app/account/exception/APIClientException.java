package com.app.account.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIClientException   extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int status;
	private final String source;
	public APIClientException(final String message, final int statusCode,  String source) {
		super(message);
		this.status = statusCode;
		this.source = source;
	}

}
