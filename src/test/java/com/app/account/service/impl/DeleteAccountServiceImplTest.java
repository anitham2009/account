package com.app.account.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.account.entity.Account;
import com.app.account.repository.IAccountRepository;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class DeleteAccountServiceImplTest {
	
	@InjectMocks
	DeleteAccountServiceImpl deleteAccountService;
	
	@Mock
	IAccountRepository accountRepository;
	
	public static final String ACCOUNT_FILE = "account.json";
	public static final String BASE_FILE_PATH = "src/test/resources/";
	@Test
	public void testDeleteAccount() throws Exception {
		Account account = (Account) retrieveObject(ACCOUNT_FILE, Account.class);
		doNothing().when(accountRepository).delete(Mockito.any());
		assertDoesNotThrow(()->deleteAccountService.deleteAccount(account));
	}
	<T> Object retrieveObject(String fileName, Class<T> contentClass)
			throws StreamReadException, DatabindException, IOException {
		File file = new File(BASE_FILE_PATH + fileName);
		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(file, contentClass);
		return object;

	}

}
