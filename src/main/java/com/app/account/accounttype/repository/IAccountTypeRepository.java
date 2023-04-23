package com.app.account.accounttype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.account.entity.AccountType;

@Repository
public interface IAccountTypeRepository extends JpaRepository<AccountType, Long>{

	public AccountType findByTypeAndIsActive(String type, String isActive);
}
