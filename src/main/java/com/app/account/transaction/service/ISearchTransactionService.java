package com.app.account.transaction.service;

import com.app.account.model.TransactionResponse;

/**
 * Search Transaction service.
 * @author Anitha Manoharan
 *
 */
public interface ISearchTransactionService {

	public TransactionResponse getTransactionDetail(Long accountId);
}
