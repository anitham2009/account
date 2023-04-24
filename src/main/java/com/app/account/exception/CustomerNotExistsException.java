package com.app.account.exception;

/**
 * Custom CustomerNotExistsException
 * @author Anitha Manoharan
 *
 */
public class CustomerNotExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerNotExistsException(final String message) {
		super(message);
		
	}

}
