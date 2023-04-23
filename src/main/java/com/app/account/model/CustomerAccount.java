package com.app.account.model;

import java.math.BigDecimal;
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
public class CustomerAccount {
	private String accountType;
	private String accountNumber;
	private BigDecimal balance;
	private List<AccountTransaction> transaction;
}
