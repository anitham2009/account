package com.app.account.transaction.service.impl;

import java.math.BigDecimal;

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

@Service
public class CreateTransactionServiceImpl implements ICreateTransactionService {

	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${transaction.service.url}")
	String transactionServiceUrl;
	
	@Value("${spring.profiles.active}")
	String activeProfile;
	
	@Override
	public TransactionResponse saveTransaction(Account account, BigDecimal creditAmount) {
		// Save Transaction
		TransactionResponse transactionResponse;
		try {
			System.out.println("Active Profile"+transactionServiceUrl);
			// Form Transaction input
			TransactionRequest transactionRequestData = formTransactionRequestData(account, creditAmount);
		    transactionResponse = restTemplate.postForObject(transactionServiceUrl,transactionRequestData,TransactionResponse.class);
		} catch (Exception e) {
			transactionResponse = TransactionErrorResponse.formErrorMessage(e.getMessage(), AccountConstants.STATUS_CODE_422);
		}
		return transactionResponse;
	}

	public TransactionRequest formTransactionRequestData(Account account, BigDecimal amount) {
		TransactionRequest transactionRequest = TransactionRequest.builder().accountId(account.getAccountId())
				.amount(amount).balance(account.getBalance()).description(AccountConstants.INITIAL_CREDIT_DESC)
				.build();
		return transactionRequest;
	}

}
