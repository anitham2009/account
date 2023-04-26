package com.app.account.accounttype.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.account.accounttype.repository.IAccountTypeRepository;
import com.app.account.accounttype.service.intf.IAccountTypeService;
import com.app.account.entity.AccountType;
import com.app.account.util.AccountConstants;

/**
 * This class is used to get Account type detail.
 * 
 * @author Anitha Manoharan
 *
 */
@Service
public class AccountTypeServiceImpl implements IAccountTypeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountTypeServiceImpl.class);
	@Autowired
	IAccountTypeRepository accountTypeRepository;

	/**
	 * Get active account type detail by given type.
	 * 
	 * @param type
	 * @return AccountType
	 */
	public AccountType retrieveAccountTypeDetail(String type) {
		LOGGER.debug("Inside retrieveAccountTypeDetail method {}", this.getClass());
		return accountTypeRepository.findByTypeAndIsActive(type, AccountConstants.Y);
	}
}
