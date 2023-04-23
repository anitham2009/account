package com.app.account.model;

import java.math.BigDecimal;
import java.util.Date;

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
public class Transaction {
	private Long transactionId;
	private String transactionNumber;
	private Long accountId;
	private String description;
	private BigDecimal transferredAmount;	
	private BigDecimal balance;
	private Date transferredDate;
}
