package com.app.account.customer.service.intf;

import com.app.account.entity.Customer;

public interface ICustomerService {

	public Customer retreiveCustomerById(Long customerId);
}
