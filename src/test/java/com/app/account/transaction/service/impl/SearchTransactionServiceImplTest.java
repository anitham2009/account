package com.app.account.transaction.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.app.account.model.TransactionResponse;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class SearchTransactionServiceImplTest {

	@InjectMocks
	SearchTransactionServiceImpl searchTransactionService;

	@Mock
	RestTemplate restTemplate;

	public static final String BASE_FILE_PATH = "src/test/resources/";
	public static final String ACCOUNT_FILE = "account.json";
	public static final String TRANSACTION_RESPONSE_FILE = "transactionresponse.json";
	public static final String ERROR_TXN_RESPONE_FILE = "errortransactonresponse.json";

	@Test
	public void testSearchTransaction() throws Exception {
		TransactionResponse transactionResponse = (TransactionResponse) retrieveObject(TRANSACTION_RESPONSE_FILE,
				TransactionResponse.class);
		ReflectionTestUtils.setField(searchTransactionService, "transactionServiceUrl",
				"http://localhost:8004/transaction");

		when(restTemplate.getForObject(anyString(), eq(TransactionResponse.class))).thenReturn(transactionResponse);
		TransactionResponse successResponse = Assertions
				.assertDoesNotThrow(() -> searchTransactionService.getTransactionDetail(1L));
		assertEquals("Success", successResponse.getMessage());
	}

	<T> Object retrieveObject(String fileName, Class<T> contentClass)
			throws StreamReadException, DatabindException, IOException {
		File file = new File(BASE_FILE_PATH + fileName);
		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(file, contentClass);
		return object;

	}

}
