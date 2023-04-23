package com.app.account.transaction.service;

import com.app.account.model.TransactionResponse;

public interface ISearchTransactionService {

	public TransactionResponse getTransactionDetail(Long accountId);
}
