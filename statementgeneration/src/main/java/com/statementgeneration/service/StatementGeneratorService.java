package com.statementgeneration.service;

import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.statementgeneration.DTO.BankStatementDTO;
import com.statementgeneration.entity.BankStatements;
import com.statementgeneration.entity.Transaction;
import com.statementgeneration.repository.StatementGeneratorRepository;
import com.statementgeneration.repository.TransactionRepository;
import com.statementgeneration.util.DummyStatementGeneration;

@Service
public class StatementGeneratorService {
	@Autowired
	private DummyStatementGeneration dummyStatementGeneration;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private StatementGeneratorRepository generatorRepository;

	private static final String STATEMENTS_DIR = "D:\\Spring Boot Practice\\Bank Statement Aggregation\\StatementsFiles";

	public ResponseEntity<BankStatements> statementGenerate(BankStatementDTO bankStatementDTO) throws IOException {
		System.out.println(bankStatementDTO);

		List<Transaction> transactionGenerated = dummyStatementGeneration.generate(bankStatementDTO);

//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
//		String formattedDate = LocalDate.now().format(formatter);
		String fileSavedName=bankStatementDTO.getCompanyCode() + bankStatementDTO.getBankCode()
		+ String.valueOf((long) bankStatementDTO.getBranchId())+System.currentTimeMillis() ;
		String fileName = fileSavedName+".csv";
		
		String filePath = STATEMENTS_DIR + "/" + fileName;
		
//		+bankStatementDTO.getCompanyName()+"/"
		
		 try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
	            writer.println("Transaction_id,Date,Amount,Debit/Credit,Company_Code,Bank_Code,Branch_ID,Description,Company_name");
	            for (Transaction transaction : transactionGenerated) {
	                writer.printf("%s,%s,%.2f,%s,%d,%d,%d,%s,%s%n",
	                        transaction.getTransactionId(),
	                        transaction.getDate(),
	                        transaction.getAmount(),
	                        transaction.getTransactionState(),
	                        transaction.getCompanyCode(),
	                        transaction.getBankCode(),
	                        transaction.getBranchId(),
	                        transaction.getDescription(),
	                        transaction.getCompanyName());
	                transactionRepository.save(transaction);
	            }
	        } catch (IOException e) {
	            throw new IOException("Error writing transactions to CSV file", e);
	        }
        
		

//        String fileUrl = awsService.uploadFile(filePath, fileName);

            BankStatements bankStatements =new BankStatements();
            bankStatements.setCompanyName(bankStatementDTO.getCompanyName());
            System.out.println(bankStatementDTO.getBankCode());
            bankStatements.setBankCode(bankStatementDTO.getBankCode());
            bankStatements.setCompanyCode(bankStatementDTO.getCompanyCode());
            bankStatements.setBranchId(bankStatementDTO.getBranchId());
            bankStatements.setStatementDate(LocalDate.now());
            bankStatements.setStatementCode(fileSavedName+System.currentTimeMillis());
            generatorRepository.save(bankStatements);
            
		return new ResponseEntity<BankStatements>(bankStatements,HttpStatus.OK);
	}

}
