package com.app.account.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.account.accounttype.service.impl.AccountTypeServiceImpl;
import com.app.account.customer.service.impl.CustomerServiceImpl;
import com.app.account.entity.Account;
import com.app.account.entity.AccountType;
import com.app.account.entity.Customer;
import com.app.account.exception.APIClientException;
import com.app.account.exception.AccountNotExistsException;
import com.app.account.exception.CustomerNotExistsException;
import com.app.account.model.TransactionResponse;
import com.app.account.repository.IAccountRepository;
import com.app.account.transaction.service.impl.SearchTransactionServiceImpl;
import com.app.account.util.AccountConstants;
import com.app.account.util.CommonConstants;
import com.app.account.util.CommonUtil;

/**
 * Test SearchAccountServiceImpl class
 * 
 * @author Anitha Manoharan
 *
 */
@ExtendWith(MockitoExtension.class)
public class SearchAccountServiceImplTest {

	@InjectMocks
	SearchAccountServiceImpl searchAccountService;

	@Mock
	IAccountRepository accountRepository;

	@Mock
	AccountTypeServiceImpl accountTypeService;

	@Mock
	CustomerServiceImpl customerService;

	@Mock
	SearchTransactionServiceImpl searchTransactionService;

	@DisplayName("Success")
	@Test
	public void testgetAccountDetail() throws IOException {
		Account account = (Account) CommonUtil.retrieveObject(CommonConstants.ACCOUNT_FILE, Account.class);
		when(accountRepository.findAccountByTypeIdAndCustomerId(any(), any())).thenReturn(account);
		Account response = searchAccountService.retrieveCustomerAccountByType(2L, 1L);
		assertNotNull(response);
		assertNotNull(response.getAccountStatus());
		assertNotNull(response.getCreatedBy());
		assertNotNull(response.getUpdatedBy());
		assertNotNull(response.getOpenDate());
		assertNotNull(response.getUpdatedDate());
		assertNotNull(response.getBranch());
	}

	@DisplayName("Success")
	@Test
	public void testSearchAccount() throws IOException {

		Customer customer = (Customer) CommonUtil.retrieveObject(CommonConstants.CUSTOMER_FILE, Customer.class);
		Account account = (Account) CommonUtil.retrieveObject(CommonConstants.ACCOUNT_FILE, Account.class);
		AccountType accountType = (AccountType) CommonUtil.retrieveObject(CommonConstants.ACCOUNTTYPE_FILE,
				AccountType.class);
		TransactionResponse transactionResponse = (TransactionResponse) CommonUtil
				.retrieveObject(CommonConstants.RETRIEVE_TRANSACTION_FILE, TransactionResponse.class);
		when(accountRepository.findAccountByTypeIdAndCustomerId(any(), any())).thenReturn(account);
		when(customerService.retreiveCustomerById(any())).thenReturn(customer);
		when(accountTypeService.retrieveAccountTypeDetail(any())).thenReturn(accountType);
		when(searchAccountService.retrieveCustomerAccountByType(any(), any())).thenReturn(account);
		when(searchTransactionService.getTransactionDetail(any())).thenReturn(transactionResponse);
		assertDoesNotThrow(() -> searchAccountService.getCustomerCurrentAccountDetail(1L));
	}

	@DisplayName("ACCOUNT_ZERO_BALANCE")
	@Test
	public void testSearchAccountZeroBalance() throws IOException {

		Customer customer = (Customer) CommonUtil.retrieveObject(CommonConstants.CUSTOMER_FILE, Customer.class);
		Account account = (Account) CommonUtil.retrieveObject(CommonConstants.ACCOUNT_ZERO_BALANCE_FILE, Account.class);
		AccountType accountType = (AccountType) CommonUtil.retrieveObject(CommonConstants.ACCOUNTTYPE_FILE,
				AccountType.class);
		when(accountRepository.findAccountByTypeIdAndCustomerId(any(), any())).thenReturn(account);
		when(customerService.retreiveCustomerById(any())).thenReturn(customer);
		when(accountTypeService.retrieveAccountTypeDetail(any())).thenReturn(accountType);
		when(searchAccountService.retrieveCustomerAccountByType(any(), any())).thenReturn(account);
		assertDoesNotThrow(() -> searchAccountService.getCustomerCurrentAccountDetail(1L));
	}

	@DisplayName("Failure response")
	@Test
	public void testAPIClientException() throws IOException {

		Customer customer = (Customer) CommonUtil.retrieveObject(CommonConstants.CUSTOMER_FILE, Customer.class);
		Account account = (Account) CommonUtil.retrieveObject(CommonConstants.ACCOUNT_FILE, Account.class);
		AccountType accountType = (AccountType) CommonUtil.retrieveObject(CommonConstants.ACCOUNTTYPE_FILE,
				AccountType.class);
		TransactionResponse transactionResponse = (TransactionResponse) CommonUtil
				.retrieveObject(CommonConstants.TRANSACTION_ERR_RESPONSE_FILE, TransactionResponse.class);
		when(accountRepository.findAccountByTypeIdAndCustomerId(any(), any())).thenReturn(account);
		when(customerService.retreiveCustomerById(any())).thenReturn(customer);
		when(accountTypeService.retrieveAccountTypeDetail(any())).thenReturn(accountType);
		when(searchAccountService.retrieveCustomerAccountByType(any(), any())).thenReturn(account);
		when(searchTransactionService.getTransactionDetail(any())).thenReturn(transactionResponse);
		Assertions.assertThrows(APIClientException.class,
				() -> searchAccountService.getCustomerCurrentAccountDetail(1L));
	}

	@DisplayName("Customer Failure response")
	@Test
	public void testCustomerNotExistsException() throws IOException {
		when(customerService.retreiveCustomerById(any())).thenReturn(null);
		CustomerNotExistsException exception = Assertions.assertThrows(CustomerNotExistsException.class,
				() -> searchAccountService.getCustomerCurrentAccountDetail(1L));
		Assertions.assertEquals(AccountConstants.CUSTOMER_NOT_EXISTS, exception.getMessage());
	}

	@DisplayName("Account not exists")
	@Test
	public void testAccountNotExistsException() throws IOException {

		Customer customer = (Customer) CommonUtil.retrieveObject(CommonConstants.CUSTOMER_FILE, Customer.class);
		AccountType accountType = (AccountType) CommonUtil.retrieveObject(CommonConstants.ACCOUNTTYPE_FILE,
				AccountType.class);
		when(accountRepository.findAccountByTypeIdAndCustomerId(any(), any())).thenReturn(null);
		when(customerService.retreiveCustomerById(any())).thenReturn(customer);
		when(accountTypeService.retrieveAccountTypeDetail(any())).thenReturn(accountType);
		Assertions.assertThrows(AccountNotExistsException.class,
				() -> searchAccountService.getCustomerCurrentAccountDetail(1L));
	}

}
