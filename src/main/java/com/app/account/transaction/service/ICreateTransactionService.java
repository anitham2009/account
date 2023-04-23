package com.app.account.transaction.service;

import java.math.BigDecimal;

import com.app.account.entity.Account;
import com.app.account.model.TransactionResponse;

public interface ICreateTransactionService {

	public TransactionResponse saveTransaction(Account account, BigDecimal creditAmount);
}
