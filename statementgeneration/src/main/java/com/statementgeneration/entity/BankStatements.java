package com.statementgeneration.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class BankStatements {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer statementId;
	@Min(value = 1000, message = "Company Code must be at least 4 digits")
    @Max(value = 999999, message = "Compay Code must be at most 6 digits")
	@Column(nullable = false)
	private int companyCode;
	
	@Min(value = 1000, message = "Bank Code must be at least 4 digits")
    @Max(value = 999999, message = "Bank Code must be at most 6 digits")
	@Column(nullable = false)
	private int bankCode;
	
	
	@Column(nullable = false)
	private Long branchId;
	
	@Column(nullable = false)
	private LocalDate statementDate;
	
	
	@Column(nullable = false, unique = true)
	private String statementCode;
	
	@NotBlank
	@Column(nullable = false)
	private String companyName;

	
	
	
	
	
	public int getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(int companyCode) {
		this.companyCode = companyCode;
	}

	public int getBankCode() {
		return bankCode;
	}

	public void setBankCode(int bankCode) {
		this.bankCode = bankCode;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public LocalDate getStatementDate() {
		return statementDate;
	}

	public void setStatementDate(LocalDate statementDate) {
		this.statementDate = statementDate;
	}

	public String getStatementCode() {
		return statementCode;
	}

	public void setStatementCode(String statementCode) {
		this.statementCode = statementCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setStatementId(Integer statementId) {
		this.statementId = statementId;
	}
	
	
	

}
