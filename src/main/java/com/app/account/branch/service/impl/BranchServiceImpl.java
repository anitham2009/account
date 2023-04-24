package com.app.account.branch.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.account.branch.repository.IBranchRepository;
import com.app.account.branch.service.intf.IBranchService;
import com.app.account.entity.Branch;
import com.app.account.util.AccountConstants;

/**
 * This class is used to get branch detail.
 * @author Anitha Manoharan
 *
 */
@Service
public class BranchServiceImpl implements IBranchService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BranchServiceImpl.class);
	@Autowired
	IBranchRepository branchRepository;
	
	/**
	 * Get active branch detail
	 * @return Branch
	 */
	@Override
	public Branch retrieveBranch() {
		LOGGER.debug("Inside retrieveBranch method {}", this.getClass());
		return branchRepository.findByIsActive(AccountConstants.Y);
	}

}
