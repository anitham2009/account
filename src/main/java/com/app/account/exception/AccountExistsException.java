package com.app.account.exception;
/**
 * Custom AccountExistsException.
 * @author Anitha Manoharan
 *
 */
public class AccountExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountExistsException(final String message) {
		super(message);
		
	}

}
