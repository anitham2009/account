package com.app.account.branch.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.account.branch.repository.IBranchRepository;
import com.app.account.entity.Branch;
import com.app.account.util.CommonConstants;
import com.app.account.util.CommonUtil;

/**
 * Test BranchServiceImpl class
 * 
 * @author Anitha Manoharan
 *
 */
@ExtendWith(MockitoExtension.class)
public class BranchServiceImplTest {
	@Mock
	IBranchRepository branchRepository;

	@InjectMocks
	BranchServiceImpl branchService;

	@DisplayName("Get branch details")
	@Test
	public void testRetrieveBranch() throws Exception {
		Branch branch = (Branch) CommonUtil.retrieveObject(CommonConstants.BRANCH_FILE, Branch.class);
		when(branchRepository.findByIsActive(any())).thenReturn(branch);
		Branch branchResponse = branchService.retrieveBranch();
		assertNotNull(branchResponse.getBranchId());
		assertNotNull(branchResponse.getBranchAddress());
		assertNotNull(branchResponse.getBranchName());
		assertNotNull(branchResponse.getIsActive());
		assertNotNull(branchResponse.getCreatedBy());
		assertNotNull(branchResponse.getUpdatedBy());
		assertNotNull(branchResponse.getUpdatedDate());
		assertNotNull(branchResponse.getCreatedDate());
		assertNotNull(branchResponse.getAccount());
		assertNotNull(branchResponse.getBranchAddress().getAddressId());
		assertNotNull(branchResponse.getBranchAddress().getAddressLine());
		assertNotNull(branchResponse.getBranchAddress().getCity());
		assertNotNull(branchResponse.getBranchAddress().getState());
		assertNotNull(branchResponse.getBranchAddress().getCountry());
		assertNotNull(branchResponse.getBranchAddress().getCreatedBy());
		assertNotNull(branchResponse.getBranchAddress().getCreatedDate());
		assertNotNull(branchResponse.getBranchAddress().getUpdatedBy());
		assertNotNull(branchResponse.getBranchAddress().getUpdatedDate());
		assertNotNull(branchResponse.getBranchAddress().getPostalCode());

	}

}
