package com.statementgeneration.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

	public Transaction() {
		super();
	}

	public Transaction(Long id, String transactionId, LocalDate date, Double amount, String transactionState,
			String description, String companyName, Integer bankCode, Long branchId, Integer companyCode) {
		super();
		this.id = id;
		this.transactionId = transactionId;
		this.date = date;
		this.amount = amount;
		this.transactionState = transactionState;
		this.description = description;
		this.companyName = companyName;
		this.bankCode = bankCode;
		this.branchId = branchId;
		this.companyCode = companyCode;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String transactionId;

	@Column(nullable = false)
	private LocalDate date;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private String transactionState;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String companyName;

	@Column(nullable = false)
	private Integer bankCode;

	@Column(nullable = false)
	private Long branchId;

	@Column(nullable = false)
	private Integer companyCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(String transactionState) {
		this.transactionState = transactionState;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getBankCode() {
		return bankCode;
	}

	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Integer getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	// Constructors, getters, and setters

}
