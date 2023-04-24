package com.app.account.customer.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.account.customer.repository.ICustomerRepository;
import com.app.account.entity.Customer;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

	@InjectMocks
	CustomerServiceImpl customerService;
	@Mock
	ICustomerRepository customerRepository;
	
	public static final String BASE_FILE_PATH = "src/test/resources/";
	public static final String CUSTOMER_FILE = "customer.json";
	
	@Test
	public void testRetrieveCustomer() throws Exception {
		Customer customer = (Customer) retrieveObject(CUSTOMER_FILE, Customer.class);
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
	
	<T> Object retrieveObject(String fileName, Class<T> contentClass)
			throws StreamReadException, DatabindException, IOException {
		File file = new File(BASE_FILE_PATH + fileName);
		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(file, contentClass);
		return object;

	}
}
