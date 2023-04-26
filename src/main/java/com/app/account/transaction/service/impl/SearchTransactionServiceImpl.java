package com.app.account.transaction.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.account.model.TransactionResponse;
import com.app.account.service.util.TransactionErrorResponse;
import com.app.account.transaction.service.ISearchTransactionService;
import com.app.account.util.AccountConstants;

/**
 * This class is used to get transaction detail from transaction service.
 * 
 * @author Anitha Manoharan
 *
 */
@Service
public class SearchTransactionServiceImpl implements ISearchTransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchTransactionServiceImpl.class);
	@Autowired
	RestTemplate restTemplate;

	@Value("${transaction.service.url}")
	String transactionServiceUrl;

	/**
	 * Get transaction detail of the given account id from transaction service. URL
	 * of the transaction service is retrieved from active spring profile.
	 * 
	 * @param accountId account id
	 * @return TransactionResponse
	 */
	@Override
	public TransactionResponse getTransactionDetail(Long accountId) {
		LOGGER.debug("Inside getTransactionDetail method {}", this.getClass());
		// Form URL of transaction service.
		TransactionResponse transactionResponse = null;
		try {
			String url = transactionServiceUrl + AccountConstants.SLASH + accountId;
			transactionResponse = restTemplate.getForObject(url, TransactionResponse.class);
		} catch (Exception e) {
			LOGGER.error("Error while processing save transaction {}", this.getClass());
			transactionResponse = TransactionErrorResponse.formErrorMessage(e.getMessage(),
					AccountConstants.STATUS_CODE_503);
		}
		return transactionResponse;
	}

}
