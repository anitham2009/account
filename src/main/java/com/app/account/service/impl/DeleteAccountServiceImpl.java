package com.app.account.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.account.entity.Account;
import com.app.account.repository.IAccountRepository;
import com.app.account.service.intf.IDeleteAccountService;

/**
 * This class is used to Delete account.
 * 
 * @author Anitha Manoharan
 *
 */
@Service
public class DeleteAccountServiceImpl implements IDeleteAccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteAccountServiceImpl.class);
	@Autowired
	IAccountRepository accountRepository;

	/**
	 * Delete account of given input account.
	 * 
	 * @param account Account.
	 */
	@Override
	public void deleteAccount(Account account) {
		LOGGER.debug("Inside deleteAccount method {}", this.getClass());
		accountRepository.delete(account);
	}

}
