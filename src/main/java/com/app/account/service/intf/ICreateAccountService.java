package com.app.account.service.intf;

import com.app.account.model.CreateAccountRequest;
import com.app.account.model.CreateAccountResponse;

public interface ICreateAccountService {

	public CreateAccountResponse createNewAccount(CreateAccountRequest createAccountDTO);
}
