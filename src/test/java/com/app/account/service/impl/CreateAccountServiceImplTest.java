package com.app.account.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.account.accounttype.service.impl.AccountTypeServiceImpl;
import com.app.account.branch.service.impl.BranchServiceImpl;
import com.app.account.customer.service.impl.CustomerServiceImpl;
import com.app.account.entity.Account;
import com.app.account.entity.AccountType;
import com.app.account.entity.Branch;
import com.app.account.entity.Customer;
import com.app.account.exception.APIClientException;
import com.app.account.exception.AccountExistsException;
import com.app.account.exception.CustomerNotExistsException;
import com.app.account.model.CreateAccountRequest;
import com.app.account.model.TransactionResponse;
import com.app.account.repository.IAccountRepository;
import com.app.account.service.util.CreateSuccessResponse;
import com.app.account.transaction.service.impl.CreateTransactionServiceImpl;
import com.app.account.util.CommonConstants;
import com.app.account.util.CommonUtil;

/**
 * 
 * Test CreateAccountServiceImpl class
 * 
 * @author Anitha Manoharan
 *
 */
@ExtendWith(MockitoExtension.class)
public class CreateAccountServiceImplTest {

	@InjectMocks
	CreateAccountServiceImpl createAccountService;
	@Mock
	CreateTransactionServiceImpl createTransactionService;

	@Mock
	CustomerServiceImpl customerService;

	@Mock
	BranchServiceImpl branchService;

	@Mock
	AccountTypeServiceImpl accountTypeService;

	@Mock
	IAccountRepository accountRepository;

	@Mock
	DeleteAccountServiceImpl deleteAccountService;

	@Mock
	SearchAccountServiceImpl searchAccountService;

	@Mock
	CreateSuccessResponse createSuccessResponse;

	@DisplayName("With Initial Credit amount")
	@Test
	public void testCreateAccountInitialCreditSuccess() throws IOException {

		Customer customer = (Customer) CommonUtil.retrieveObject(CommonConstants.CUSTOMER_FILE, Customer.class);
		Account account = (Account) CommonUtil.retrieveObject(CommonConstants.ACCOUNT_FILE, Account.class);
		AccountType accountType = (AccountType) CommonUtil.retrieveObject(CommonConstants.ACCOUNTTYPE_FILE,
				AccountType.class);
		Branch branch = (Branch) CommonUtil.retrieveObject(CommonConstants.BRANCH_FILE, Branch.class);
		TransactionResponse transactionResponse = (TransactionResponse) CommonUtil
				.retrieveObject(CommonConstants.TRANSACTION_RESPONSE_FILE, TransactionResponse.class);
		CreateAccountRequest request = (CreateAccountRequest) CommonUtil
				.retrieveObject(CommonConstants.CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE, CreateAccountRequest.class);

		when(customerService.retreiveCustomerById(any())).thenReturn(customer);
		when(branchService.retrieveBranch()).thenReturn(branch);
		when(accountTypeService.retrieveAccountTypeDetail(any())).thenReturn(accountType);
		when(searchAccountService.retrieveCustomerAccountByType(any(), any())).thenReturn(null);
		when(createTransactionService.saveTransaction(any(), any())).thenReturn(transactionResponse);
		doReturn(account).when(accountRepository).saveAndFlush(Mockito.any());
		assertDoesNotThrow(() -> createAccountService.createNewAccount(request));
	}

	@DisplayName("Initial Credit amount is 0")
	@Test
	public void testCreateAccountInitialCreditZeroSuccess() throws IOException {

		Customer customer = (Customer) CommonUtil.retrieveObject(CommonConstants.CUSTOMER_FILE, Customer.class);
		Account account = (Account) CommonUtil.retrieveObject(CommonConstants.ACCOUNT_FILE, Account.class);
		AccountType accountType = (AccountType) CommonUtil.retrieveObject(CommonConstants.ACCOUNTTYPE_FILE,
				AccountType.class);
		Branch branch = (Branch) CommonUtil.retrieveObject(CommonConstants.BRANCH_FILE, Branch.class);
		CreateAccountRequest request = (CreateAccountRequest) CommonUtil
				.retrieveObject(CommonConstants.CREATE_ACCOUNT_REQUEST_ZERO_CREDIT_FILE, CreateAccountRequest.class);

		when(customerService.retreiveCustomerById(any())).thenReturn(customer);
		when(branchService.retrieveBranch()).thenReturn(branch);
		when(accountTypeService.retrieveAccountTypeDetail(any())).thenReturn(accountType);
		when(searchAccountService.retrieveCustomerAccountByType(any(), any())).thenReturn(null);
		doReturn(account).when(accountRepository).saveAndFlush(Mockito.any());
		assertDoesNotThrow(() -> createAccountService.createNewAccount(request));
	}

	@DisplayName("Create Transacton failure")
	@Test
	public void testCreateTransactionFailure() throws IOException {

		Customer customer = (Customer) CommonUtil.retrieveObject(CommonConstants.CUSTOMER_FILE, Customer.class);
		Account account = (Account) CommonUtil.retrieveObject(CommonConstants.ACCOUNT_FILE, Account.class);
		AccountType accountType = (AccountType) CommonUtil.retrieveObject(CommonConstants.ACCOUNTTYPE_FILE,
				AccountType.class);
		Branch branch = (Branch) CommonUtil.retrieveObject(CommonConstants.BRANCH_FILE, Branch.class);
		TransactionResponse transactionResponse = (TransactionResponse) CommonUtil
				.retrieveObject(CommonConstants.ERROR_TXN_RESPONE_FILE, TransactionResponse.class);
		CreateAccountRequest request = (CreateAccountRequest) CommonUtil
				.retrieveObject(CommonConstants.CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE, CreateAccountRequest.class);

		when(customerService.retreiveCustomerById(any())).thenReturn(customer);
		when(branchService.retrieveBranch()).thenReturn(branch);
		when(accountTypeService.retrieveAccountTypeDetail(any())).thenReturn(accountType);
		when(searchAccountService.retrieveCustomerAccountByType(any(), any())).thenReturn(null);
		when(createTransactionService.saveTransaction(any(), any())).thenReturn(transactionResponse);
		doReturn(account).when(accountRepository).saveAndFlush(Mockito.any());
		assertThrows(APIClientException.class, () -> createAccountService.createNewAccount(request));
	}

	@DisplayName("Invalid customer input")
	@Test
	public void testCustomerNotExistsException() throws IOException {
		CreateAccountRequest request = (CreateAccountRequest) CommonUtil
				.retrieveObject(CommonConstants.INVALID_CUSTOMER_INPUT_FILE, CreateAccountRequest.class);
		when(customerService.retreiveCustomerById(any())).thenReturn(null);
		assertThrows(CustomerNotExistsException.class, () -> createAccountService.createNewAccount(request));
	}

	@DisplayName("Current Account already exists")
	@Test
	public void testCurrentAccountExistsException() throws IOException {
		Customer customer = (Customer) CommonUtil.retrieveObject(CommonConstants.CUSTOMER_FILE, Customer.class);
		Account account = (Account) CommonUtil.retrieveObject(CommonConstants.ACCOUNT_FILE, Account.class);
		AccountType accountType = (AccountType) CommonUtil.retrieveObject(CommonConstants.ACCOUNTTYPE_FILE,
				AccountType.class);
		CreateAccountRequest request = (CreateAccountRequest) CommonUtil
				.retrieveObject(CommonConstants.CREATE_ACCOUNT_REQUEST_INITIAL_CREDIT_FILE, CreateAccountRequest.class);

		when(customerService.retreiveCustomerById(any())).thenReturn(customer);
		when(accountTypeService.retrieveAccountTypeDetail(any())).thenReturn(accountType);
		when(searchAccountService.retrieveCustomerAccountByType(any(), any())).thenReturn(account);

		assertThrows(AccountExistsException.class, () -> createAccountService.createNewAccount(request));
	}

}
