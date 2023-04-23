package com.app.account.accounttype.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.account.accounttype.repository.IAccountTypeRepository;
import com.app.account.accounttype.service.intf.IAccountTypeService;
import com.app.account.entity.AccountType;

@Service
public class AccountTypeServiceImpl implements IAccountTypeService {

	@Autowired
	IAccountTypeRepository accountTypeRepository;
	
	public AccountType retrieveAccountTypeDetail(String type) {
		return accountTypeRepository.findByTypeAndIsActive(type,"Y");
	}
}
