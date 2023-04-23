package com.app.account.exception;

public class AccountExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountExistsException(final String message) {
		super(message);
		
	}

}
