package com.app.account.accounttype.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.account.accounttype.repository.IAccountTypeRepository;
import com.app.account.entity.AccountType;
import com.app.account.util.CommonConstants;
import com.app.account.util.CommonUtil;

/**
 * Test AccountTypeServiceImpl class
 * 
 * @author Anitha Manoharan
 *
 */
@ExtendWith(MockitoExtension.class)
public class AccountTypeServiceImplTest {

	@InjectMocks
	AccountTypeServiceImpl accountTypeService;

	@Mock
	IAccountTypeRepository accountTypeRepository;

	@DisplayName("Test retrieval of Account type")
	@Test
	public void testAccountType() throws Exception {
		AccountType accountType = (AccountType) CommonUtil.retrieveObject(CommonConstants.ACCOUNTTYPE_FILE,
				AccountType.class);
		when(accountTypeRepository.findByTypeAndIsActive(any(), any())).thenReturn(accountType);
		AccountType accountTypeResponse = accountTypeService.retrieveAccountTypeDetail("Current Account");
		assertEquals("Current Account", accountTypeResponse.getType());
		assertNotNull(accountTypeResponse.getCreatedBy());
		assertNotNull(accountTypeResponse.getCreatedDate());
		assertNotNull(accountTypeResponse.getUpdatedBy());
		assertNotNull(accountTypeResponse.getUpdatedDate());
		assertNotNull(accountTypeResponse.getIsActive());
		assertNotNull(accountTypeResponse.getAccount());
	}

}
