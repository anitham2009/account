package com.app.account.model;
import lombok.Data;

@Data
public class APIClientExceptionMessage {

	private String errorMessage;
	private int statusCode;
	private String source;
}
