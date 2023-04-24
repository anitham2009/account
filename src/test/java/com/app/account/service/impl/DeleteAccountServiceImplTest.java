package com.app.account.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.account.entity.Account;
import com.app.account.repository.IAccountRepository;
import com.app.account.util.CommonConstants;
import com.app.account.util.CommonUtil;

/**
 * Test DeleteAccountServiceImpl class
 * 
 * @author Anitha Manoharan
 *
 */
@ExtendWith(MockitoExtension.class)
public class DeleteAccountServiceImplTest {

	@InjectMocks
	DeleteAccountServiceImpl deleteAccountService;

	@Mock
	IAccountRepository accountRepository;

	@DisplayName("Delete account")
	@Test
	public void testDeleteAccount() throws Exception {
		Account account = (Account) CommonUtil.retrieveObject(CommonConstants.ACCOUNT_FILE, Account.class);
		doNothing().when(accountRepository).delete(Mockito.any());
		assertDoesNotThrow(() -> deleteAccountService.deleteAccount(account));
	}

}
