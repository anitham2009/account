package com.app.account.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * This class is used to get Account information by the given input.
 * 
 * @author Anitha Manoharan
 *
 */
@Service
public class SearchAccountServiceImpl implements ISearchAccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchAccountServiceImpl.class);
	@Autowired
	IAccountRepository accountRepository;

	@Autowired
	IAccountTypeService accountTypeService;

	@Autowired
	ICustomerService customerService;

	@Autowired
	ISearchTransactionService searchTransactionService;

	/**
	 * Retrieve Account details of the customer for given account type.
	 * @param typeId Account Type
	 * @param customerId Customer Id
	 * @return Account Account
	 */
	@Override
	public Account retrieveCustomerAccountByType(Long typeId, Long customerId) {
		LOGGER.debug("Inside retrieveCustomerAccountByType method {}", this.getClass());
		Account existingAccount = accountRepository.findAccountByTypeIdAndCustomerId(typeId, customerId);
		return existingAccount;
	}

	/**
	 * Retrieve Customer, Account and its transaction detail for the given customer.
	 * Before get Account details of the customer, check customer availability 
	 * If customer does not exists then throw exception of type CustomerNotExistsException.
	 * Get Account details of the given customer by account type "Current Account".
	 * If account does not exists then throw exception of type AccountNotExistsException.
	 * If account balance is 0 then skip taking transaction detail and return response.
	 * If account balance is > 0 then invokes transaction service to get relevant transaction detail.
	 * If response from transaction service is "Success" then return response with customer,account and transaction detail.
	 * If response from transaction service is "Failure" then throw exception of type APIClientException by setting error response return from
	 * transaction service.
	 * @param customerId Customer Id
	 * @return SearchAccountResponse
	 * 
	 */
	@Override
	public SearchAccountResponse getCustomerCurrentAccountDetail(Long customerId) {
		LOGGER.debug("Inside getCustomerCurrentAccountDetail method {}", this.getClass());
		SearchAccountResponse response = null;
		//Get customer details.
		Customer customer = customerService.retreiveCustomerById(customerId);
		if (customer == null) {
			LOGGER.error("Customer does not exists {}", this.getClass());
			throw new CustomerNotExistsException(AccountConstants.CUSTOMER_NOT_EXISTS);
		} else {
			//Get account type details.
			AccountType accountType = accountTypeService.retrieveAccountTypeDetail(AccountConstants.CURRENT_ACCOUNT);
			//Get account details.
			Account existingAccount = accountRepository.findAccountByTypeIdAndCustomerId(accountType.getTypeId(),
					customerId);
			if (existingAccount == null) {
				LOGGER.error("Account does not exists {}", this.getClass());
				throw new AccountNotExistsException(AccountConstants.ACCOUNT_NOT_EXISTS);
			} else {
				//Check balance amount in Account.
				if (existingAccount.getBalance().compareTo(BigDecimal.ZERO) > 0) {
					LOGGER.info("Account has balance amount > 0 {}", this.getClass());
					TransactionResponse transactionResponse = searchTransactionService
							.getTransactionDetail(existingAccount.getAccountId());
					//Check response from Transaction service.
					if (transactionResponse.getMessage().equalsIgnoreCase(AccountConstants.SUCCESS) ) {
						LOGGER.info("Success response from Transaction Service {}", this.getClass());
						SearchAccountSuccessResponse successResponse = new SearchAccountSuccessResponse(); 
						response = successResponse.formResponseData(customer, existingAccount,
								transactionResponse.getTransaction());
					} else {
						LOGGER.error("Error response from Transaction service {}", this.getClass());
						throw new APIClientException(transactionResponse.getErrorMessage().getMessage(),
								transactionResponse.getErrorMessage().getStatusCode(),
								transactionResponse.getErrorMessage().getSource());
					}
				} else {
					//Account balance is 0.
					LOGGER.info("Account Balance is 0 {}", this.getClass());
					SearchAccountSuccessResponse successResponse = new SearchAccountSuccessResponse(); 
					response = successResponse.formResponseData(customer, existingAccount, null);
				}

			}

		}
		return response;
	}
	
	
	 
	 
}
