package com.app.account.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.app.account.entity.Account;
import com.app.account.model.CreateAccountResponse;
import com.app.account.model.SuccessResponse;
import com.app.account.util.AccountConstants;

/**
 * Create Success Response for Account Creation.
 *
 * @author Anitha Manoharan
 *
 */
@Component
public class CreateSuccessResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateSuccessResponse.class);

	/**
	 * This method sets account number, customer Id and transaction number in the
	 * success response.
	 * 
	 * @param account
	 * @param transactionNumber
	 * @return CreateAccountResponse
	 */
	public CreateAccountResponse formSuccessResponse(Account account, String transactionNumber) {
		LOGGER.debug("Inside formSuccessResponse method {}", this.getClass());
		SuccessResponse successResponse = SuccessResponse.builder().accountNumber(account.getAccountNumber())
				.customerId(account.getCustomer().getCustomerId()).transactionNumber(transactionNumber).build();
		CreateAccountResponse response = CreateAccountResponse.builder()
				.status(AccountConstants.ACCOUNT_CREATED_SUCCESS).successResponse(successResponse).build();
		return response;
	}
}
