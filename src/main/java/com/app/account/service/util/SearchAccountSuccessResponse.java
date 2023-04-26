package com.app.account.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.app.account.entity.Account;
import com.app.account.entity.Customer;
import com.app.account.model.AccountTransaction;
import com.app.account.model.CustomerAccount;
import com.app.account.model.SearchAccountResponse;
import com.app.account.model.Transaction;

/**
 * This class form Success response for the given customer with details of
 * Account, Customer and Transaction
 * 
 * @author Anitha Manoharan
 *
 */
@Component
public class SearchAccountSuccessResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchAccountSuccessResponse.class);

	/**
	 * Set the values in response model those values retrieved from search account.
	 * 
	 * @param customer     Customer
	 * @param account      Account
	 * @param transactions List<Transaction>
	 * @return SearchAccountResponse
	 */
	public SearchAccountResponse formResponseData(Customer customer, Account account, List<Transaction> transactions) {
		LOGGER.debug("Inside formResponseData method {}", this.getClass());
		SearchAccountResponse response = SearchAccountResponse.builder().name(customer.getFirstName())
				.surname(customer.getSurname()).customerAccount(formCustomerAccountInfo(account, transactions)).build();
		return response;
	}

	/**
	 * Set values of Account, transaction detail of Account
	 * 
	 * @param account      Account
	 * @param transactions List<Transaction>
	 * @return CustomerAccount
	 */
	private CustomerAccount formCustomerAccountInfo(Account account, List<Transaction> transactions) {
		LOGGER.debug("Inside formCustomerAccountInfo method {}", this.getClass());
		List<AccountTransaction> accountTransactions = new ArrayList<>();
		// Set values of Transaction retrieved from Transaction service.
		if (transactions != null) {
			accountTransactions = formAccountTransactionInfo(transactions);
		}

		CustomerAccount customerAccount = CustomerAccount.builder().accountNumber(account.getAccountNumber())
				.accountType(account.getAccountType().getType()).balance(account.getBalance())
				.transaction(accountTransactions).build();

		return customerAccount;
	}

	/**
	 * Set transaction details retrieved from Transaction service.
	 * 
	 * @param transctions List<Transaction>
	 * @return List<AccountTransaction>
	 */
	private List<AccountTransaction> formAccountTransactionInfo(List<Transaction> transctions) {
		LOGGER.debug("Inside formAccountTransactionInfo method {}", this.getClass());
		// Iterate list of transactions and add it into response model.
		List<AccountTransaction> accountTransactions = transctions.stream().map(transaction -> {
			AccountTransaction response = AccountTransaction.builder().accountId(transaction.getAccountId())
					.balance(transaction.getBalance()).description(transaction.getDescription())
					.transactionId(transaction.getTransactionId()).transactionNumber(transaction.getTransactionNumber())
					.transferredAmount(transaction.getTransferredAmount())
					.transferredDate(transaction.getTransferredDate()).build();
			return response;
		}).collect(Collectors.toList());

		return accountTransactions;
	}
}
