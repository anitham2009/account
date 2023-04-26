package com.app.account.customer.service.intf;

import com.app.account.entity.Customer;

/**
 * Customer service
 * @author Anitha Manoharan
 *
 */
public interface ICustomerService {

	public Customer retreiveCustomerById(Long customerId);
}
