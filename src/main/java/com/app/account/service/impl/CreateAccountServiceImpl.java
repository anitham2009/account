package com.app.account.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * This class is used to create the Account of the given customer and its
 * transaction. Check existence of input before doing operation and return
 * success/error response or throw exception based upon the response. Converts
 * entity object to into response model before returning the response.
 * 
 * @author Anitha Manoharan
 *
 */
@Service
public class CreateAccountServiceImpl implements ICreateAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateAccountServiceImpl.class);
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

	/**
	 * This method is used to create Current Account and its transaction based upon
	 * given input. Check given customer existence, if customer not exists then
	 * throw exception of type CustomerNotExistsException Check Account existence
	 * for the given customer of type Current account. If exists then throw
	 * exception of type AccountExistsException. Retrieve Branch and Account type,
	 * Customer information to associate with Account. Assume Branch, Account Type
	 * are already exists in database as it loads data while server startup. Save
	 * Account with Branch, Customer, Account Type and balance. If initialCredit
	 * amount in request is 0 then skip adding transaction. else invokes transaction
	 * service to create transaction for newly created account. If response from
	 * transaction service is "Success" then return response with customer
	 * id,account number and transaction number. If response from transaction
	 * service is "Failure" then throw exception of type APIClientException by
	 * setting error response return from transaction service.
	 * 
	 * @param createAccountRequest Create Account Request
	 * @return CreateAccountResponse
	 */
	@Override
	public CreateAccountResponse createNewAccount(CreateAccountRequest createAccountRequest) {
		LOGGER.debug("Inside createNewAccount method {}", this.getClass());
		CreateAccountResponse response = null;
		Long customerId = createAccountRequest.getCustomerId();
		// Check existence of customer.
		Customer customer = customerService.retreiveCustomerById(customerId);
		if (customer == null) {
			LOGGER.error("Customer does not exists {}", this.getClass());
			throw new CustomerNotExistsException(AccountConstants.CUSTOMER_NOT_EXISTS);
		} else {
			// Check current account existence for customer
			AccountType accountType = accountTypeService.retrieveAccountTypeDetail(AccountConstants.CURRENT_ACCOUNT);
			Account existingAccount = searchAccountService.retrieveCustomerAccountByType(accountType.getTypeId(),
					customerId);
			if (existingAccount != null) {
				LOGGER.error("Account exists {}", this.getClass());
				throw new AccountExistsException(AccountConstants.ACCOUNT_EXISTS);
			} else {
				// Get branch
				Branch branch = branchService.retrieveBranch();
				BigDecimal creditAmount = BigDecimal.valueOf(createAccountRequest.getInitialCredit());

				// Form Account input
				Account account = formAccountInput(customer, branch, accountType, creditAmount);
				// Save Account
				account = accountRepository.saveAndFlush(account);
				LOGGER.info("Current Account created.");
				// Check initialCredit amount to save transaction.
				if (createAccountRequest.getInitialCredit().longValue() != 0L) {
					// Save Transaction.
					TransactionResponse transactionResponse = createTransactionService.saveTransaction(account,
							creditAmount);
					// Check response message from Transaction Service.
					if (transactionResponse.getMessage().equalsIgnoreCase(AccountConstants.SUCCESS)) {
						CreateSuccessResponse createSuccessResponse = new CreateSuccessResponse();
						response = createSuccessResponse.formSuccessResponse(account,
								transactionResponse.getTransaction().get(0).getTransactionNumber());
					} else {
						LOGGER.error("Error response from Transaction Service {}", this.getClass());
						// Failure transaction, revert account creation
						deleteAccountService.deleteAccount(account);
						throw new APIClientException(transactionResponse.getErrorMessage().getMessage(),
								transactionResponse.getErrorMessage().getStatusCode(),
								transactionResponse.getErrorMessage().getSource());
					}
				} else {
					// Form response message without transaction as initialCredit is 0.
					CreateSuccessResponse createSuccessResponse = new CreateSuccessResponse();
					response = createSuccessResponse.formSuccessResponse(account, AccountConstants.EMPTY_STRING);
				}
			}

		}
		return response;
	}

	/**
	 * 
	 * Form Account entity object to save in database. Create Account number and set
	 * it in Account object. Set Audit Field values along with Customer, Branch,
	 * Account Type info.
	 * 
	 * @param customer
	 * @param branch
	 * @param accountType
	 * @param initialCredit
	 * @return Account
	 */
	private Account formAccountInput(Customer customer, Branch branch, AccountType accountType,
			BigDecimal initialCredit) {
		LOGGER.debug("Inside formAccountInput method {}", this.getClass());

		Account account = Account.builder().accountNumber(AccountNumberUtil.createAccountNumber())
				.accountStatus(AccountConstants.ACTIVE).branch(branch).accountType(accountType).customer(customer)
				.balance(initialCredit).createdBy(AccountConstants.SYSTEM).openDate(new Date())
				.updatedBy(AccountConstants.SYSTEM).updatedDate(new Date()).build();
		return account;
	}
}
