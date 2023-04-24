package com.app.account.transaction.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.account.entity.Account;
import com.app.account.model.TransactionRequest;
import com.app.account.model.TransactionResponse;
import com.app.account.service.util.TransactionErrorResponse;
import com.app.account.transaction.service.ICreateTransactionService;
import com.app.account.util.AccountConstants;

/**
 * This class is used to save transaction in transaction service.
 *
 * @author Anitha Manoharan
 *
 */
@Service
public class CreateTransactionServiceImpl implements ICreateTransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateTransactionServiceImpl.class);
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${transaction.service.url}")
	String transactionServiceUrl;
	/**
	 * This method invoked Transaction service to save the transaction of the saved
	 * current account with credit amount. Reads transaction service URL from active spring profile.
	 * @param account Account
	 * @param creditAmount credit amount in the account
	 * @return TransactionResponse
	 */
	@Override
	public TransactionResponse saveTransaction(Account account, BigDecimal creditAmount) {
		// Save Transaction
		TransactionResponse transactionResponse;
		try {
			LOGGER.debug("Inside saveTransaction method {}", this.getClass());
			// Form Transaction input
			TransactionRequest transactionRequestData = formTransactionRequestData(account, creditAmount);
		    LOGGER.info("Invoking transaction service {}", this.getClass());
			transactionResponse = restTemplate.postForObject(transactionServiceUrl,transactionRequestData,TransactionResponse.class);
			LOGGER.info("Received response from transaction service {}", this.getClass());
		} catch (Exception e) {
			LOGGER.error("Error while processing save transaction {}", this.getClass());
			transactionResponse = TransactionErrorResponse.formErrorMessage(e.getMessage(), AccountConstants.STATUS_CODE_422);
		}
		return transactionResponse;
	}

	/**
	 * Create input data for Transaction service to save transaction.
	 * @param account Account
	 * @param amount amount
	 * @return TransactionRequest
	 */
	public TransactionRequest formTransactionRequestData(Account account, BigDecimal amount) {
		LOGGER.debug("Inside formTransactionRequestData method {}", this.getClass());
		TransactionRequest transactionRequest = TransactionRequest.builder().accountId(account.getAccountId())
				.amount(amount).balance(account.getBalance()).description(AccountConstants.INITIAL_CREDIT_DESC)
				.build();
		return transactionRequest;
	}

}
