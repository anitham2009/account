package com.app.account.accounttype.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.app.account.accounttype.repository.IAccountTypeRepository;
import com.app.account.entity.AccountType;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class AccountTypeServiceImplTest {

	@InjectMocks
	AccountTypeServiceImpl accountTypeService;
	
	@Mock
	IAccountTypeRepository accountTypeRepository;
	
	public static final String BASE_FILE_PATH = "src/test/resources/";
	public static final String ACCOUNTTYPE_FILE = "accounttype.json";
	
	@Test
	public void testAccountType() throws Exception {
		AccountType accountType = (AccountType) retrieveObject(ACCOUNTTYPE_FILE, AccountType.class);
		when(accountTypeRepository.findByTypeAndIsActive(any(), any())).thenReturn(accountType);
		AccountType accountTypeResponse = accountTypeService.retrieveAccountTypeDetail("Current Account");
		assertEquals("Current Account", accountTypeResponse.getType());
		assertNotNull(accountTypeResponse.getCreatedBy());
		assertNotNull(accountTypeResponse.getCreatedDate());
		assertNotNull(accountTypeResponse.getUpdatedBy());
		assertNotNull(accountTypeResponse.getUpdatedDate());
		assertNotNull(accountTypeResponse.getIsActive());
		assertNotNull(accountTypeResponse.getAccount());
	}
	
	<T> Object retrieveObject(String fileName, Class<T> contentClass)
			throws StreamReadException, DatabindException, IOException {
		File file = new File(BASE_FILE_PATH + fileName);
		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(file, contentClass);
		return object;

	}
}
