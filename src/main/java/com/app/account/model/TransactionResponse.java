package com.app.account.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionResponse {

	private String message;
	private List<Transaction> transaction;
	private ErrorMessage errorMessage; 
}
