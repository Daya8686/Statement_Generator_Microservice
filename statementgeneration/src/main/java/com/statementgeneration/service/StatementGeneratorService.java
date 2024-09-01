package com.statementgeneration.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;
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
import com.statementgeneration.exceptions.DuplicateStatementCodeException;
import com.statementgeneration.repository.StatementGeneratorRepository;
import com.statementgeneration.repository.TransactionRepository;
import com.statementgeneration.util.ApiResponse;
import com.statementgeneration.util.DummyStatementGeneration;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Service
public class StatementGeneratorService {
	@Autowired
	private DummyStatementGeneration dummyStatementGeneration;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private StatementGeneratorRepository generatorRepository;
	
	@Autowired
	private AWSService awsService;

	
	private static final String STATEMENTS_DIR = "D:\\Spring Boot Practice\\Bank Statement Aggregation\\StatementsFiles";
	

	public ResponseEntity<?> statementGenerate(BankStatementDTO bankStatementDTO) throws IOException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		String formattedDate = LocalDate.now().format(formatter);
		
		System.out.println(bankStatementDTO);
		String key=bankStatementDTO.getCompanyCode() + bankStatementDTO.getBankCode()+bankStatementDTO.getBranchId()+formattedDate;
		String keyToCheck=key+".csv";
		if(awsService.checkKeyExists(keyToCheck)) {
			System.out.println("Inside Check key method after key present");
			String downloadFileToLocal = awsService.downloadFileToLocal(STATEMENTS_DIR,keyToCheck);
			if(downloadFileToLocal!=null) {
//				int transTogen=(int)Math.random()*5;
//				bankStatementDTO.setNumberOfTransactionsGenerate(transTogen);
				List<Transaction> transactionGenerated = dummyStatementGeneration.generate(bankStatementDTO);
				String filePath=STATEMENTS_DIR+"\\"+keyToCheck;
				System.out.println(filePath);
				 try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) { // 'true' enables appending mode
					 System.out.println("Updating the statement file because file is already present");
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
			                
			                //Saving transaction data in transaction table
			                transactionRepository.save(transaction);
			            }
			        } catch (IOException e) {
			            throw new IOException("Error writing transactions to CSV file", e);
			        }
		        
				

		        String fileUrl = awsService.uploadFile(filePath, keyToCheck);
				 
				
			}
//			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),"Existing file updated with new transactions!!",null));
			return new ResponseEntity(new ApiResponse(HttpStatus.CREATED.value(),"Existing file updated with new transactions!!",null, true),HttpStatus.CREATED);
			
			
		}
		else {
		return newStatementGenerator(bankStatementDTO, formattedDate);
		}
	}

//NEW STATEMENT GENERATOR
	@Transactional
	private ResponseEntity<BankStatements> newStatementGenerator(BankStatementDTO bankStatementDTO,
			String formattedDate) throws IOException {
		System.out.println("Creating new statement file!");
		List<Transaction> transactionGenerated = dummyStatementGeneration.generate(bankStatementDTO);

		String fileSavedName=bankStatementDTO.getCompanyCode() + bankStatementDTO.getBankCode()+bankStatementDTO.getBranchId()
//		+ String.valueOf((long) bankStatementDTO.getBranchId())+System.currentTimeMillis() 
		+formattedDate;
		String fileName = fileSavedName+".csv";
		
		String filePath = STATEMENTS_DIR + "\\" + fileName;
		System.out.println(filePath);
//		+bankStatementDTO.getCompanyName()+"/"
		
		//Writing BankStatement code on top of Transaction writing in CSV file code 
		//Because if is there any exception then file will not get created 
		 BankStatements bankStatements =new BankStatements();
		 try {
           
            bankStatements.setCompanyName(bankStatementDTO.getCompanyName());
            System.out.println(bankStatementDTO.getBankCode());
            bankStatements.setBankCode(bankStatementDTO.getBankCode());
            bankStatements.setCompanyCode(bankStatementDTO.getCompanyCode());
            bankStatements.setBranchId(bankStatementDTO.getBranchId());
            bankStatements.setStatementDate(LocalDate.now());
            bankStatements.setStatementCode(fileName);
           
            generatorRepository.save(bankStatements);
            }
            catch(DuplicateStatementCodeException ex){
            	throw new DuplicateStatementCodeException("Statement code must be unique", HttpStatus.INTERNAL_SERVER_ERROR);
            }
		
		
		
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
	                
	                //Saving transaction data in transaction table
	                transactionRepository.save(transaction);
	            }
	        } catch (IOException e) {
	            throw new IOException("Error writing transactions to CSV file", e);
	        }
        
		
            
//            UPLOADING FILE TO AWS
            String fileUrl = awsService.uploadFile(filePath, fileName);
            
            //DELETE AFTER UPLOAD
            if (bankStatementDTO.isDeleteAfterUpload()) {
                new File(filePath).delete();
            }

		return new ResponseEntity<BankStatements>(bankStatements,HttpStatus.CREATED);
	}
	
	
	
}
