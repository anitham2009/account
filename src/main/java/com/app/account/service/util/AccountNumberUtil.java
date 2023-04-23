package com.app.account.service.util;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.app.account.util.AccountConstants;

@Component
public class AccountNumberUtil {
	public static String createAccountNumber() {
		Random random = new Random();
		int randomNumber = Math.abs(random.nextInt());
		String accountNumber = AccountConstants.ACCOUNT_NUMBER_PREFIX + randomNumber;
		return accountNumber;
				
	}

}
