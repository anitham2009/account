package com.app.account.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.account.entity.Customer;

/**
 * Customer object repository.
 * Used to do database operations of Customer.
 * @author Anitha Manoharan
 *
 */
@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long>{
	
	public Customer findByCustomerIdAndIsActive(Long customerId, String isActive);

}
