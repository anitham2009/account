package com.app.account.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="branch")
@Getter
@Setter
@NoArgsConstructor
public class Branch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long branchId;
	private String branchName;
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address branchAddress;
	private String isActive;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updatedDate;
	@OneToMany(mappedBy="branch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Account> account;
}
