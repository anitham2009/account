package com.app.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.account.entity.Account;
import com.app.account.repository.IAccountRepository;
import com.app.account.service.intf.IDeleteAccountService;

@Service
public class DeleteAccountServiceImpl implements IDeleteAccountService {

	@Autowired
	IAccountRepository accountRepository;
	
	@Override
	public void deleteAccount(Account account) {
		accountRepository.delete(account);
	}

}
