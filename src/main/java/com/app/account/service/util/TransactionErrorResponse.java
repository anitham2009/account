package com.app.account.service.util;

import org.springframework.stereotype.Component;

import com.app.account.model.ErrorMessage;
import com.app.account.model.TransactionErrorMessage;
import com.app.account.model.TransactionResponse;
import com.app.account.util.AccountConstants;

@Component
public class TransactionErrorResponse {

	public static TransactionResponse formErrorMessage(String message, int statusCode) {
		ErrorMessage errorMessage = TransactionErrorMessage.formErrorMessage(message, statusCode);
		TransactionResponse errorResponse = TransactionResponse.builder().errorMessage(errorMessage).message(AccountConstants.FAILURE).build();
		return errorResponse;
	}
}
