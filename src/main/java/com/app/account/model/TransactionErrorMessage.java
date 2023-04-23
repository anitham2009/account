package com.app.account.model;

import org.springframework.stereotype.Component;

import com.app.account.util.AccountConstants;

@Component
public class TransactionErrorMessage {
	public static ErrorMessage formErrorMessage(String message, int statusCode) {
		ErrorMessage errorMessage = ErrorMessage.builder().message(message).statusCode(statusCode).source(AccountConstants.TRANSACTION_SERVICE).build();
		return errorMessage;
	}
}
