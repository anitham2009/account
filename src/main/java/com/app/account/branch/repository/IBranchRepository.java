package com.app.account.branch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.account.entity.Branch;

/**
 * Repository class of Branch entity.
 * 
 * @author Anitha Manoharan
 *
 */
@Repository
public interface IBranchRepository extends JpaRepository<Branch, Long> {

	public Branch findByIsActive(String isActive);
}
