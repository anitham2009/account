package com.app.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.account.entity.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long>{
	
	@Query(value = "select a.* from account a inner join account_type at on a.type_id=at.type_id inner join customer c on c.customer_id=a.customer_id where at.type_id=:typeId and c.customer_id=:customerId",nativeQuery = true)
	public Account findAccountByTypeIdAndCustomerId(@Param("typeId") Long typeId, @Param("customerId") Long customerId);

	//@Query(value = "select * from account where customer_id = :customerId", nativeQuery=true)
	//public Account findAccountByCustomerId(Long customerId);
}
