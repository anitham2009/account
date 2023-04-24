package com.app.account.branch.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.account.branch.repository.IBranchRepository;
import com.app.account.entity.Branch;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class BranchServiceImplTest {
	@Mock
	IBranchRepository branchRepository;
	
	@InjectMocks
	BranchServiceImpl branchService;
	
	public static final String BASE_FILE_PATH = "src/test/resources/";
	public static final String BRANCH_FILE = "branch.json";
	
	@Test
	public void testRetrieveBranch() throws Exception {
		Branch branch = (Branch) retrieveObject(BRANCH_FILE, Branch.class);
		when(branchRepository.findByIsActive(any())).thenReturn(branch);
		Branch branchResponse =  branchService.retrieveBranch();
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
	
	<T> Object retrieveObject(String fileName, Class<T> contentClass)
			throws StreamReadException, DatabindException, IOException {
		File file = new File(BASE_FILE_PATH + fileName);
		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(file, contentClass);
		return object;

	}

}
