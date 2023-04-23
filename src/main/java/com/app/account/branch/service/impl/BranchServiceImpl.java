package com.app.account.branch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.account.branch.repository.IBranchRepository;
import com.app.account.branch.service.intf.IBranchService;
import com.app.account.entity.Branch;

@Service
public class BranchServiceImpl implements IBranchService {

	@Autowired
	IBranchRepository branchRepository;
	
	@Override
	public Branch retrieveBranch() {
		return branchRepository.findByIsActive("Y");
	}

}
