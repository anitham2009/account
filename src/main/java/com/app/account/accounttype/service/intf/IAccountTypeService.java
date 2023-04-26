package com.app.account.accounttype.service.intf;

import com.app.account.entity.AccountType;

/**
 * Account type service.
 * @author Anitha Manoharan
 *
 */
public interface IAccountTypeService {

	public AccountType retrieveAccountTypeDetail(String type);
}
