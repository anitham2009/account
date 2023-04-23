package com.app.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessResponse {
	private String accountNumber;
	private Long customerId;
	private String transactionNumber;
}
