package com.statementgeneration.controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.statementgeneration.DTO.BankStatementDTO;
import com.statementgeneration.entity.BankStatements;
import com.statementgeneration.service.StatementGeneratorService;

import jakarta.validation.Valid;

@RestController
public class StatementGeneratorController {
	
	@Autowired
	private StatementGeneratorService generatorService;
	
	@PostMapping("/generate")
	public ResponseEntity<?> BankStatementsGenerator(@Valid @RequestBody BankStatementDTO bankStatementDTO) throws IOException{
		System.out.println(bankStatementDTO);
		return generatorService.statementGenerate(bankStatementDTO);
		
		
	}

}
