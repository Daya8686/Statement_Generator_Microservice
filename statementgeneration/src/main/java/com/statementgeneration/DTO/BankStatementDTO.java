package com.statementgeneration.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class BankStatementDTO {
	
	@NotBlank
	@Min(value = 1000, message = "Company Code must be at least 4 digits")
	@Max(value = 999999, message = "Company Code must be at most 6 digits")
	private int companyCode;
	
	@NotBlank
	@Min(value = 1000, message = "Bank Code must be at least 4 digits")
    @Max(value = 999999, message = "Bank Code must be at most 6 digits")
	private int BankCode;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="branch_id")
    private Long branchId;
	
	@NotBlank
	private int numberOfTransactionsGenerate;
	
	@NotBlank
	private boolean deleteAfterUpload;
	
	@NotBlank
	private String companyName;

	public int getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(int companyCode) {
		this.companyCode = companyCode;
	}

	public int getBankCode() {
		return BankCode;
	}

	public void setBankCode(int bankCode) {
		BankCode = bankCode;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	

	

	public int getNumberOfTransactionsGenerate() {
		return numberOfTransactionsGenerate;
	}

	public void setNumberOfTransactionsGenerate(int numberOfTransactionsGenerate) {
		this.numberOfTransactionsGenerate = numberOfTransactionsGenerate;
	}

	public boolean isDeleteAfterUpload() {
		return deleteAfterUpload;
	}

	public void setDeleteAfterUpload(boolean deleteAfterUpload) {
		this.deleteAfterUpload = deleteAfterUpload;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "BankStatementDTO [companyCode=" + companyCode + ", BankCode=" + BankCode + ", branchId=" + branchId
				+ ", noumberOfTransactionToGenerate=" + numberOfTransactionsGenerate + ", deleteAfterUpload="
				+ deleteAfterUpload + ", companyName=" + companyName + "]";
	}

	
	
}
