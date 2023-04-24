package com.app.account.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Address")
@Getter
@Setter
@NoArgsConstructor
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	private String addressLine;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	@OneToMany(mappedBy="customerAddress", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Customer> customer;
	@OneToMany(mappedBy="branchAddress", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Branch> branch;
}
