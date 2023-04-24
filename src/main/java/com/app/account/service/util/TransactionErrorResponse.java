package com.app.account.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.app.account.model.ErrorMessage;
import com.app.account.model.TransactionErrorMessage;
import com.app.account.model.TransactionResponse;
import com.app.account.util.AccountConstants;

/**
 * This class used to set error response of Transaction.
 * @author Anitha Manoharan
 *
 */
@Component
public class TransactionErrorResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionErrorResponse.class);
	/**
	 * Set the error message, status code retrieved from Transaction Service into response model.
	 * @param message message
	 * @param statusCode status code
	 * @return TransactionResponse
	 */
	public static TransactionResponse formErrorMessage(String message, int statusCode) {
		LOGGER.debug("Inside formErrorMessage method {}", TransactionErrorResponse.class);
		ErrorMessage errorMessage = TransactionErrorMessage.formErrorMessage(message, statusCode);
		TransactionResponse errorResponse = TransactionResponse.builder().errorMessage(errorMessage).message(AccountConstants.FAILURE).build();
		return errorResponse;
	}
}
