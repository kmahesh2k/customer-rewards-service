package com.myprojects.customerrewardsservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Transaction")
public class Transaction {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Integer transId;

	@Column
	private BigDecimal transAmt;

	@Column
	private LocalDateTime transDate;

}
