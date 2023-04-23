package com.app.account.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.account.accounttype.service.intf.IAccountTypeService;
import com.app.account.branch.service.intf.IBranchService;
import com.app.account.customer.service.intf.ICustomerService;
import com.app.account.entity.Account;
import com.app.account.entity.AccountType;
import com.app.account.entity.Branch;
import com.app.account.entity.Customer;
import com.app.account.exception.APIClientException;
import com.app.account.exception.AccountExistsException;
import com.app.account.exception.CustomerNotExistsException;
import com.app.account.model.CreateAccountRequest;
import com.app.account.model.CreateAccountResponse;
import com.app.account.model.TransactionResponse;
import com.app.account.repository.IAccountRepository;
import com.app.account.service.intf.ICreateAccountService;
import com.app.account.service.intf.IDeleteAccountService;
import com.app.account.service.intf.ISearchAccountService;
import com.app.account.service.util.AccountNumberUtil;
import com.app.account.service.util.CreateSuccessResponse;
import com.app.account.transaction.service.ICreateTransactionService;
import com.app.account.util.AccountConstants;

@Service
public class CreateAccountServiceImpl implements ICreateAccountService {

	@Autowired
	ICreateTransactionService createTransactionService;


	@Autowired
	ICustomerService customerService;

	@Autowired
	IBranchService branchService;

	@Autowired
	IAccountTypeService accountTypeService;

	@Autowired
	IAccountRepository accountRepository;

	@Autowired
	IDeleteAccountService deleteAccountService;

	@Autowired
	ISearchAccountService searchAccountService;

	@Override
	public CreateAccountResponse createNewAccount(CreateAccountRequest createAccountRequest) {
		CreateAccountResponse response = null;
		Long customerId = createAccountRequest.getCustomerId();
		// Check existence of customer.
		Customer customer = customerService.retreiveCustomerById(customerId);
		if (customer == null) {
			throw new CustomerNotExistsException(AccountConstants.CUSTOMER_NOT_EXISTS);
		} else {
			// Check current account existence for customer
			AccountType accountType = accountTypeService.retrieveAccountTypeDetail(AccountConstants.CURRENT_ACCOUNT);
			Account existingAccount = searchAccountService.retrieveCustomerAccountByType(accountType.getTypeId(),
					customerId);
			if (existingAccount != null) {
				throw new AccountExistsException(AccountConstants.ACCOUNT_EXISTS);
			} else {
				// Get branch
				Branch branch = branchService.retrieveBranch();
				BigDecimal creditAmount = BigDecimal.valueOf(createAccountRequest.getInitialCredit());

				// Form Account input
				Account account = formAccountInput(customer, branch, accountType, creditAmount);
				// Save Account
				account = accountRepository.saveAndFlush(account);
				if (createAccountRequest.getInitialCredit().longValue() != 0L) {
					TransactionResponse transactionResponse = createTransactionService.saveTransaction(account,
							creditAmount);
					if (transactionResponse.getMessage().equalsIgnoreCase(AccountConstants.SUCCESS)) {
						 CreateSuccessResponse createSuccessResponse = new CreateSuccessResponse();
						response = createSuccessResponse.formSuccessResponse(account,
								transactionResponse.getTransaction().get(0).getTransactionNumber());
					} else {
						// Failure transaction, revert account creation
						deleteAccountService.deleteAccount(account);
						throw new APIClientException(transactionResponse.getErrorMessage().getMessage(),
								transactionResponse.getErrorMessage().getStatusCode(),
								transactionResponse.getErrorMessage().getSource());
					}
				} else {
					CreateSuccessResponse createSuccessResponse = new CreateSuccessResponse();
					response = createSuccessResponse.formSuccessResponse(account, AccountConstants.EMPTY_STRING);
				}
			}

		}
		return response;
	}

	
	private Account formAccountInput(Customer customer, Branch branch, AccountType accountType,
			BigDecimal initialCredit) {
		Account account = Account.builder().accountNumber(AccountNumberUtil.createAccountNumber())
				.accountStatus(AccountConstants.ACTIVE).branch(branch).accountType(accountType).customer(customer)
				.balance(initialCredit).createdBy(AccountConstants.SYSTEM).openDate(new Date())
				.updatedBy(AccountConstants.SYSTEM).updatedDate(new Date()).build();
		return account;
	}
}
