package com.app.account.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.account.customer.repository.ICustomerRepository;
import com.app.account.customer.service.intf.ICustomerService;
import com.app.account.entity.Customer;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	ICustomerRepository customerRepsitory;
	
	@Override
	public Customer retreiveCustomerById(Long customerId) {
		return customerRepsitory.findByCustomerIdAndIsActive(customerId, "Y");
	}

}
