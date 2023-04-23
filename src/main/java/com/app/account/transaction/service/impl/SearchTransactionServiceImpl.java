package com.app.account.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.account.model.TransactionResponse;
import com.app.account.transaction.service.ISearchTransactionService;
import com.app.account.util.AccountConstants;

@Service
public class SearchTransactionServiceImpl implements ISearchTransactionService {

	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${transaction.service.url}")
	String transactionServiceUrl;
	@Override
	public TransactionResponse getTransactionDetail(Long accountId) {
		String url = transactionServiceUrl+AccountConstants.SLASH+accountId;
		TransactionResponse r = restTemplate.getForObject(url, TransactionResponse.class);
		return r;
	}

	

}
