package com.app.account.model;

import java.math.BigDecimal;

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
public class TransactionRequest {

	private Long accountId;
	private BigDecimal amount;	
	private BigDecimal balance;
	private String description;
	
}
