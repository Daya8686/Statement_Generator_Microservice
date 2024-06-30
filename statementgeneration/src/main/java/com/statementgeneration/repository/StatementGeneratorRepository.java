package com.statementgeneration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.statementgeneration.entity.BankStatements;

@Repository
public interface StatementGeneratorRepository extends JpaRepository<BankStatements, Integer>{

}
