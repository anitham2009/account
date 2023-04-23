package com.app.account.resource;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.account.accounttype.repository.IAccountTypeRepository;
import com.app.account.branch.repository.IBranchRepository;
import com.app.account.customer.repository.ICustomerRepository;
import com.app.account.entity.Account;
import com.app.account.entity.AccountType;
import com.app.account.entity.Branch;
import com.app.account.entity.Customer;
import com.app.account.exception.APIClientException;
import com.app.account.exception.AccountExistsException;
import com.app.account.exception.AccountNotExistsException;
import com.app.account.exception.CustomerNotExistsException;
import com.app.account.model.CreateAccountRequest;
import com.app.account.model.CreateAccountResponse;
import com.app.account.model.ErrorResponse;
import com.app.account.model.InputErrorMessage;
import com.app.account.model.SearchAccountResponse;
import com.app.account.repository.IAccountRepository;
import com.app.account.service.intf.ICreateAccountService;
import com.app.account.service.intf.ISearchAccountService;
import com.app.account.util.AccountConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/account")
@Api(value = "/account", tags = "Account Resource")
public class AccountController {



	@Autowired
	ICreateAccountService createAccountService;
	
	@Autowired
	ISearchAccountService searchAccountService;
	
	@Autowired
	IAccountRepository accountrep;
	
	
	@Autowired
	ICustomerRepository customerRepsitory;
	
	@Autowired
	IBranchRepository branchRepository;
	
	@Autowired
	IAccountTypeRepository acty;
	
	@GetMapping(value ="/customer", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Customer> imHealthy0() {
		//Account account = accountrep.findAccountByTypeIdAndCustomerId(2L, 1L);
		Customer account = customerRepsitory.findByCustomerIdAndIsActive(1L, "Y");
		return new ResponseEntity<Customer>(account,HttpStatus.OK);
	}
	
	@GetMapping(value ="/branch" , produces = MediaType.APPLICATION_JSON_VALUE)
	public Branch imHealthy1() {
		//Account account = accountrep.findAccountByTypeIdAndCustomerId(2L, 1L);
		Branch account = branchRepository.findByIsActive("Y");
		return account;
	}
	@GetMapping(value ="/accounttype" , produces = MediaType.APPLICATION_JSON_VALUE)
	public AccountType imHealthy2() {
		//Account account = accountrep.findAccountByTypeIdAndCustomerId(2L, 1L);
		AccountType account = acty.findByTypeAndIsActive("Current Account","Y");
		return account;
	}
	

	@GetMapping(value ="/account",produces = MediaType.APPLICATION_JSON_VALUE )
	public Account imHealthy() {
		Account account = accountrep.findAccountByTypeIdAndCustomerId(2L, 1L);
		return account;
	}
	

	@ApiOperation(value = "Create Current Account")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateAccountResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Account Exists Exception",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "API Client Exception",
            content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer Not Exists Exception",
            content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content)})
	@PostMapping
	public ResponseEntity<CreateAccountResponse> createNewAccount(@Valid @RequestBody CreateAccountRequest createAccountDTO) {
		CreateAccountResponse response = createAccountService.createNewAccount(createAccountDTO);
		return new ResponseEntity<CreateAccountResponse>(response, HttpStatus.CREATED);
	}
	
	/*
	 * @ApiOperation(value = "Create Current Account")
	 * 
	 * @ApiResponses(value = {
	 * 
	 * @ApiResponse(responseCode = "200", description = "Success", content =
	 * {@Content(mediaType = "application/json", schema = @Schema(implementation =
	 * CreateAccountResponse.class))}),
	 * 
	 * @ApiResponse(responseCode = "409", description = "Account Exists Exception",
	 * content = @Content),
	 * 
	 * @ApiResponse(responseCode = "422", description = "API Client Exception",
	 * content = @Content),
	 * 
	 * @ApiResponse(responseCode = "404", description =
	 * "Customer Not Exists Exception", content = @Content),
	 * 
	 * @ApiResponse(responseCode = "500", description = "Internal Server Error",
	 * content = @Content)})
	 * 
	 * @PostMapping("/newAccount") public ResponseEntity<TransactionResponse>
	 * createNewAccountMock(@Valid @RequestBody CreateAccountRequest
	 * createAccountDTO) { TransactionResponse response =
	 * createAccountService.createNewAccountMock(createAccountDTO); return new
	 * ResponseEntity<TransactionResponse>(response, HttpStatus.CREATED); }
	 */
	
	@ApiOperation(value = "Get Current Account")
	@GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchAccountResponse> getUserAccountInfo(@ApiParam(example="1") @PathVariable(value="customerId") Long customerId) {
		SearchAccountResponse response = searchAccountService.getCustomerCurrentAccountDetail(customerId);
		return new ResponseEntity<SearchAccountResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Handle MethodArgumentNotValidException.
	 * 
	 * @param ex MethodArgumentNotValidException
	 * @return ErrorMessage
	 */

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public InputErrorMessage handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		InputErrorMessage errorResponse = InputErrorMessage.builder().code(AccountConstants.STATUS_CODE_400).message(AccountConstants.INPUT_ERROR)
				.severity(AccountConstants.ERROR).source(AccountConstants.ACCOUNT_SERVICE).build();
		List<InputErrorMessage.InnerError> innerErrors = new ArrayList<>();
		errorResponse.setInnerErrors(innerErrors);
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			InputErrorMessage.InnerError innerError = InputErrorMessage.InnerError.builder().message(error.getDefaultMessage())
					.source(fieldName).build();
			innerErrors.add(innerError);
		});
		return errorResponse;
	}
	
	/**
	 * Handles Internal server error.
	 * 
	 * @param ex Exception
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleInternalServerError(Exception ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode(AccountConstants.STATUS_CODE_500).source(AccountConstants.ACCOUNT_SERVICE).build();
		return errorResponse;
	}

	/**
	 * Handles Internal server error.
	 * 
	 * @param ex Exception
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(APIClientException.class)
	public ErrorResponse handleAPIClientException(APIClientException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode(ex.getStatus()).source(ex.getSource()).build();
		return errorResponse;
	}
	

	/**
	 * Handle Account exists exception
	 * 
	 * @param ex RecipeNotFoundException
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(AccountExistsException.class)
	public ErrorResponse handleAccountExistsException(AccountExistsException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode(AccountConstants.STATUS_CODE_409).source(AccountConstants.ACCOUNT_SERVICE).build();
		return errorResponse;
	}

	
	/**
	 * Handle Customer not exists exception
	 * 
	 * @param ex RecipeNotFoundException
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CustomerNotExistsException.class)
	public ErrorResponse handleCustomerNotExistsException(CustomerNotExistsException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode(AccountConstants.STATUS_CODE_404).source(AccountConstants.ACCOUNT_SERVICE).build();
		return errorResponse;
	}
	
	
	/**
	 * Handle Account not exists exception
	 * 
	 * @param ex RecipeNotFoundException
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(AccountNotExistsException.class)
	public ErrorResponse handleAccountNotExistsException(AccountNotExistsException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode(AccountConstants.STATUS_CODE_404).source(AccountConstants.ACCOUNT_SERVICE).build();
		return errorResponse;
	}
	
}
