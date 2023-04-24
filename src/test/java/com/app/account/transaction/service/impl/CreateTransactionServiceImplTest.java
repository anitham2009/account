package com.app.account.transaction.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.app.account.entity.Account;
import com.app.account.model.TransactionResponse;
import com.app.account.util.CommonConstants;
import com.app.account.util.CommonUtil;

/**
 * Test CreateTransactionServiceImpl class
 * 
 * @author Anitha Manoharan
 *
 */
@ExtendWith(MockitoExtension.class)
public class CreateTransactionServiceImplTest {

	@InjectMocks
	CreateTransactionServiceImpl createTransactionService;

	@Mock
	RestTemplate restTemplate;

	@DisplayName("Create Transction")
	@Test
	public void testCreateTransaction() throws Exception {
		Account account = (Account) CommonUtil.retrieveObject(CommonConstants.ACCOUNT_FILE, Account.class);
		TransactionResponse transactionResponse = (TransactionResponse) CommonUtil
				.retrieveObject(CommonConstants.TRANSACTION_RESPONSE_FILE, TransactionResponse.class);
		ReflectionTestUtils.setField(createTransactionService, CommonConstants.TRANSACTION_SERVICE_URL_NAME,
				CommonConstants.TRANSACTION_SERVICE_URL);

		when(restTemplate.postForObject(anyString(), any(Object.class), eq(TransactionResponse.class)))
				.thenReturn(transactionResponse);
		TransactionResponse successResponse = Assertions
				.assertDoesNotThrow(() -> createTransactionService.saveTransaction(account, new BigDecimal(1)));
		assertEquals(CommonConstants.SUCCESS, successResponse.getMessage());
	}

	@DisplayName("Create Transaction Error response")
	@Test
	public void testCreateTransactionException() throws Exception {
		TransactionResponse successResponse = Assertions
				.assertDoesNotThrow(() -> createTransactionService.saveTransaction(null, new BigDecimal(1)));
		assertEquals(CommonConstants.FAILURE, successResponse.getMessage());
	}

}
