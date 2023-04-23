package com.app.account.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.app.account.entity.Account;
import com.app.account.entity.Customer;
import com.app.account.model.AccountTransaction;
import com.app.account.model.CustomerAccount;
import com.app.account.model.SearchAccountResponse;
import com.app.account.model.Transaction;

@Component
public class SearchAccountSuccessResponse {

	public SearchAccountResponse formResponseData(Customer customer, Account account, List<Transaction> transactions) {
		SearchAccountResponse response = SearchAccountResponse.builder()
				.name(customer.getFirstName())
				.surname(customer.getSurname())
				.customerAccount(formCustomerAccountInfo(account, transactions)).build();
		return response;
	}
	
	private CustomerAccount formCustomerAccountInfo(Account account, List<Transaction> transactions) {
		List<AccountTransaction> accountTransactions = new ArrayList<>();
		if(transactions != null) {
			accountTransactions = formAccountTransactionInfo(transactions);
		}
		
		CustomerAccount customerAccount = CustomerAccount.builder()
				.accountNumber(account.getAccountNumber())
				.accountType(account.getAccountType().getType())
				.balance(account.getBalance())
				.transaction(accountTransactions).build();
		
		return customerAccount;
	}
	
	private  List<AccountTransaction> formAccountTransactionInfo(List<Transaction> transctions) {
		List<AccountTransaction> accountTransactions = transctions.stream().map(transaction -> {
					AccountTransaction response = AccountTransaction.builder()
					.accountId(transaction.getAccountId())
					.balance(transaction.getBalance())
					.description(transaction.getDescription())
					.transactionId(transaction.getTransactionId())
					.transactionNumber(transaction.getTransactionNumber())
					.transferredAmount(transaction.getTransferredAmount())
					.transferredDate(transaction.getTransferredDate()).build(); 
				return response;
			}).collect(Collectors.toList());
		
		return accountTransactions;
	}
}
