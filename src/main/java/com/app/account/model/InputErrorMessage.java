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
@Builder
public class InputErrorMessage {
	private String severity;
	private int code;
	private String message;
	private String source;
	private List<InnerError> innerErrors;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class InnerError {
		private String message;
		private String source;
	}
}
