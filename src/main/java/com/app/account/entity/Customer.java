package com.app.account.entity;

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
@Table(name="Customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	private String firstName;
	private String surname;
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address customerAddress;
	private String gender;
	private String contactNumber;
	private String dateOfBirth;
	private String isActive;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updatedDate;
	
}
