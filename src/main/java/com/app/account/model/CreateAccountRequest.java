package com.app.account.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
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
@ApiModel(description="Create Account Request")
public class CreateAccountRequest {
	@NotNull(message = "Customer Id is mandatory")
	@DecimalMin(value = "1")
	private Long customerId;
	@NotNull(message = "Initial Credit is mandatory")
	@DecimalMin(value = "0")
	private Long initialCredit;
}
