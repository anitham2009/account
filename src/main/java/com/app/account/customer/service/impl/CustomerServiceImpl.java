package com.app.account.customer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.account.customer.repository.ICustomerRepository;
import com.app.account.customer.service.intf.ICustomerService;
import com.app.account.entity.Customer;
import com.app.account.util.AccountConstants;

/**
 * This class is used to get the customer detail.
 * 
 * @author Anitha Manoharan
 *
 */
@Service
public class CustomerServiceImpl implements ICustomerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	@Autowired
	ICustomerRepository customerRepsitory;

	/**
	 * Get active customer detail by customer id.
	 * 
	 * @param customerId customer id
	 * @return Customer
	 */
	@Override
	public Customer retreiveCustomerById(Long customerId) {
		LOGGER.debug("Inside retreiveCustomerById method {}", this.getClass());
		return customerRepsitory.findByCustomerIdAndIsActive(customerId, AccountConstants.Y);
	}

}
