package com.app.account.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	private String accountNumber;
	@ManyToOne
	@JoinColumn(name="branch_id")
	private Branch branch;
	@ManyToOne
	@JoinColumn(name="type_id")
	private AccountType accountType;
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	private String accountStatus;
	private BigDecimal balance;
	private Date openDate;
	private String createdBy;
	private String updatedBy;
	private Date updatedDate;
}
