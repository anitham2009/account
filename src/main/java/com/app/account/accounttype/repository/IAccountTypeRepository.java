package com.app.account.accounttype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.account.entity.AccountType;

/**
 * This class is used to do database operations of AccountType
 * 
 * @author Anitha Manoharan
 *
 */
@Repository
public interface IAccountTypeRepository extends JpaRepository<AccountType, Long> {

	public AccountType findByTypeAndIsActive(String type, String isActive);
}
