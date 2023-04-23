package com.app.account.service.util;

import org.springframework.stereotype.Component;

import com.app.account.entity.Account;
import com.app.account.model.CreateAccountResponse;
import com.app.account.model.SuccessResponse;
import com.app.account.util.AccountConstants;

@Component
public class CreateSuccessResponse {

	public CreateAccountResponse formSuccessResponse(Account account, String transactionNumber) {
		SuccessResponse successResponse = SuccessResponse.builder()
				.accountNumber(account.getAccountNumber())
				.customerId(account.getCustomer().getCustomerId()).transactionNumber(transactionNumber)
				.build();
		CreateAccountResponse response = CreateAccountResponse.builder().status(AccountConstants.ACCOUNT_CREATED_SUCCESS)
				.successResponse(successResponse).build();
		return response;
	}
}
