package com.app.account.service.intf;

import com.app.account.model.CreateAccountRequest;
import com.app.account.model.CreateAccountResponse;
/**
 * Create Account Service
 * @author Anitha Manoharan
 *
 */
public interface ICreateAccountService {

	public CreateAccountResponse createNewAccount(CreateAccountRequest createAccountDTO);
}
