package com.app.account.transaction.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.app.account.entity.Account;
import com.app.account.exception.APIClientException;
import com.app.account.model.TransactionRequest;
import com.app.account.model.TransactionResponse;
import com.app.account.util.AccountConstants;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionServiceImplTest {
	
	@InjectMocks
	CreateTransactionServiceImpl createTransactionService;
	
	@Mock
	RestTemplate restTemplate;
	
	public static final String BASE_FILE_PATH = "src/test/resources/";
	public static final String ACCOUNT_FILE = "account.json";
	public static final String TRANSACTION_RESPONSE_FILE = "transactionresponse.json";
	public static final String ERROR_TXN_RESPONE_FILE = "errortransactonresponse.json";
	@Test
	public void testCreateTransaction() throws Exception {
		Account account = (Account) retrieveObject(ACCOUNT_FILE, Account.class);
		TransactionResponse transactionResponse = (TransactionResponse) retrieveObject(TRANSACTION_RESPONSE_FILE,
				TransactionResponse.class);
		ReflectionTestUtils.setField(createTransactionService, "transactionServiceUrl", "http://transaction:8004/transaction");
		
		  when(restTemplate.postForObject(anyString(),any(Object.class),eq(TransactionResponse.class)))
          .thenReturn(transactionResponse);
		TransactionResponse successResponse = Assertions.assertDoesNotThrow(()-> createTransactionService.saveTransaction(account, new BigDecimal(1)));
		assertEquals("Success", successResponse.getMessage());
	}
	
	
	@Test
	public void testCreateTransactionException() throws Exception {
		TransactionResponse successResponse = Assertions.assertDoesNotThrow(()-> createTransactionService.saveTransaction(null, new BigDecimal(1)));
		assertEquals("Failure", successResponse.getMessage());
	}
	
	<T> Object retrieveObject(String fileName, Class<T> contentClass)
			throws StreamReadException, DatabindException, IOException {
		File file = new File(BASE_FILE_PATH + fileName);
		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(file, contentClass);
		return object;

	}

}
