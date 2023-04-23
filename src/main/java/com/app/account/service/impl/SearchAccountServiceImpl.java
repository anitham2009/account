package com.app.account.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.account.accounttype.service.intf.IAccountTypeService;
import com.app.account.customer.service.intf.ICustomerService;
import com.app.account.entity.Account;
import com.app.account.entity.AccountType;
import com.app.account.entity.Customer;
import com.app.account.exception.APIClientException;
import com.app.account.exception.AccountNotExistsException;
import com.app.account.exception.CustomerNotExistsException;
import com.app.account.model.SearchAccountResponse;
import com.app.account.model.TransactionResponse;
import com.app.account.repository.IAccountRepository;
import com.app.account.service.intf.ISearchAccountService;
import com.app.account.service.util.SearchAccountSuccessResponse;
import com.app.account.transaction.service.ISearchTransactionService;
import com.app.account.util.AccountConstants;

@Service
public class SearchAccountServiceImpl implements ISearchAccountService {

	@Autowired
	IAccountRepository accountRepository;

	@Autowired
	IAccountTypeService accountTypeService;

	@Autowired
	ICustomerService customerService;

	@Autowired
	ISearchTransactionService searchTransactionService;

	@Override
	public Account retrieveCustomerAccountByType(Long typeId, Long customerId) {
		Account existingAccount = accountRepository.findAccountByTypeIdAndCustomerId(typeId, customerId);
		return existingAccount;
	}

	@Override
	public SearchAccountResponse getCustomerCurrentAccountDetail(Long customerId) {
		SearchAccountResponse response = null;
		Customer customer = customerService.retreiveCustomerById(customerId);
		if (customer == null) {
			throw new CustomerNotExistsException(AccountConstants.CUSTOMER_NOT_EXISTS);
		} else {
			AccountType accountType = accountTypeService.retrieveAccountTypeDetail(AccountConstants.CURRENT_ACCOUNT);
			Account existingAccount = accountRepository.findAccountByTypeIdAndCustomerId(accountType.getTypeId(),
					customerId);
			if (existingAccount == null) {
				throw new AccountNotExistsException(AccountConstants.ACCOUNT_NOT_EXISTS);
			} else {
				if (existingAccount.getBalance().compareTo(BigDecimal.ZERO) > 0) {
					TransactionResponse transactionResponse = searchTransactionService
							.getTransactionDetail(existingAccount.getAccountId());
					if (transactionResponse.getMessage().equalsIgnoreCase(AccountConstants.SUCCESS) ) {
						SearchAccountSuccessResponse successResponse = new SearchAccountSuccessResponse(); 
						response = successResponse.formResponseData(customer, existingAccount,
								transactionResponse.getTransaction());
					} else {
						throw new APIClientException(transactionResponse.getErrorMessage().getMessage(),
								transactionResponse.getErrorMessage().getStatusCode(),
								transactionResponse.getErrorMessage().getSource());
					}
				} else {
					SearchAccountSuccessResponse successResponse = new SearchAccountSuccessResponse(); 
					response = successResponse.formResponseData(customer, existingAccount, null);
				}

			}

		}
		return response;
	}
	
	
	 
	 
}
