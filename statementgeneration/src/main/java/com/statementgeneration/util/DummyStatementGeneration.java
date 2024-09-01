package com.statementgeneration.util;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.statementgeneration.DTO.BankStatementDTO;
import com.statementgeneration.entity.Transaction;

@Component
public class DummyStatementGeneration {

	private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int TRANSACTION_ID_LENGTH = 8;
	private static final Random RANDOM = new Random();

	public List<Transaction> generate(BankStatementDTO bankStatementDTO) {
		System.out.println(bankStatementDTO);
		List<Transaction> transactions = new ArrayList<>();
		for (int i = 0; i < bankStatementDTO.getNumberOfTransactionsGenerate(); i++) {
			Transaction transaction = new Transaction();
			
			transaction.setTransactionId(generateTransactionId());
			transaction.setDate(LocalDate.now());
			
			Double randomAmount = Math.random() * 1000;
	        DecimalFormat df = new DecimalFormat("#.00");
	        String formattedAmount = df.format(randomAmount);
	        double amount = Double.parseDouble(formattedAmount);
	    
			transaction.setAmount(amount);
			transaction.setCompanyName(bankStatementDTO.getCompanyName());
			transaction.setCompanyCode(bankStatementDTO.getCompanyCode());
			transaction.setTransactionState(Math.random() < 0.7 ? "CR" : "DR");
			transaction.setBankCode(bankStatementDTO.getBankCode());
			transaction.setBranchId(bankStatementDTO.getBranchId());
			transaction.setDescription(bankStatementDTO.getCompanyName() + "_" + bankStatementDTO.getCompanyCode() + "_"
					+ bankStatementDTO.getBankCode() + "_" + bankStatementDTO.getBranchId());
			transactions.add(transaction);
		}
		return transactions;
	}

	private static String generateTransactionId() {
		StringBuilder transactionId = new StringBuilder(TRANSACTION_ID_LENGTH);
		for (int i = 0; i < TRANSACTION_ID_LENGTH; i++) {
			transactionId.append(ALPHANUMERIC_CHARACTERS.charAt(RANDOM.nextInt(ALPHANUMERIC_CHARACTERS.length())));
		}
		return transactionId.toString();
	}

}
