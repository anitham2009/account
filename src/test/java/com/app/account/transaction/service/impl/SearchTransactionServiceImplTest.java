package com.app.account.transaction.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.app.account.model.TransactionResponse;
import com.app.account.util.CommonConstants;
import com.app.account.util.CommonUtil;

/**
 * Test SearchTransactionServiceImpl class
 * 
 * @author Anitha Manoharan
 *
 */
@ExtendWith(MockitoExtension.class)
public class SearchTransactionServiceImplTest {

	@InjectMocks
	SearchTransactionServiceImpl searchTransactionService;

	@Mock
	RestTemplate restTemplate;

	@DisplayName("Get Transaction details")
	@Test
	public void testSearchTransaction() throws Exception {
		TransactionResponse transactionResponse = (TransactionResponse) CommonUtil
				.retrieveObject(CommonConstants.TRANSACTION_RESPONSE_FILE, TransactionResponse.class);
		ReflectionTestUtils.setField(searchTransactionService, CommonConstants.TRANSACTION_SERVICE_URL_NAME,
				CommonConstants.TRANSACTION_SERVICE_URL);

		when(restTemplate.getForObject(anyString(), eq(TransactionResponse.class))).thenReturn(transactionResponse);
		TransactionResponse successResponse = Assertions
				.assertDoesNotThrow(() -> searchTransactionService.getTransactionDetail(1L));
		assertEquals(CommonConstants.SUCCESS, successResponse.getMessage());
	}

}
