package com.app.account.service.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.app.account.util.AccountConstants;
/**
 * Creates Account number of the Account.
 * @author Anitha Manoharan
 *
 */
@Component
public class AccountNumberUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountNumberUtil.class);
	/**
	 * Create Account number from Random class with prefix static string.
	 * @return String
	 */
	public static String createAccountNumber() {
		LOGGER.debug("Inside createAccountNumber method {}", AccountNumberUtil.class);
		Random random = new Random();
		int randomNumber = Math.abs(random.nextInt());
		String accountNumber = AccountConstants.ACCOUNT_NUMBER_PREFIX + randomNumber;
		return accountNumber;
				
	}

}
