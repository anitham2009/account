package com.app.account.exception;

public class AccountNotExistsException  extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotExistsException(final String message) {
		super(message);
		
	}
}
