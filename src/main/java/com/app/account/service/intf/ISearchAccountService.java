package com.app.account.service.intf;

import com.app.account.entity.Account;
import com.app.account.model.SearchAccountResponse;

public interface ISearchAccountService {

	public Account retrieveCustomerAccountByType(Long typeId, Long customerId);
	
	public SearchAccountResponse getCustomerCurrentAccountDetail(Long customerId);
}
