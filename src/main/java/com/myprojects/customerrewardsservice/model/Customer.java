package com.myprojects.customerrewardsservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "Customer")
public class Customer {

	@Id
	private Integer custId;

	@Column
	private String custName;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "custFKey", referencedColumnName = "custId")
	private List<Transaction> transactions;
}
