package com.app.account.customer.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.account.customer.repository.ICustomerRepository;
import com.app.account.entity.Customer;
import com.app.account.util.CommonConstants;
import com.app.account.util.CommonUtil;

/**
 * Test CustomerServiceImpl class
 * @author Anitha Manoharan
 *
 */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

	@InjectMocks
	CustomerServiceImpl customerService;
	@Mock
	ICustomerRepository customerRepository;
	
	@DisplayName("Get customer details")
	@Test
	public void testRetrieveCustomer() throws Exception {
		Customer customer = (Customer) CommonUtil.retrieveObject(CommonConstants.CUSTOMER_FILE, Customer.class);
		when(customerRepository.findByCustomerIdAndIsActive(any(), any())).thenReturn(customer);
		Customer customerResponse = customerService.retreiveCustomerById(1L);
		assertNotNull(customerResponse);
		assertNotNull(customerResponse.getContactNumber());
		assertNotNull(customerResponse.getGender());
		assertNotNull(customerResponse.getDateOfBirth());
		assertNotNull(customerResponse.getIsActive());
		assertNotNull(customerResponse.getCreatedBy());
		assertNotNull(customerResponse.getUpdatedBy());
		assertNotNull(customerResponse.getUpdatedDate());
		assertNotNull(customerResponse.getCreatedDate());
		assertNotNull(customerResponse.getCustomerAddress());
	}

}
