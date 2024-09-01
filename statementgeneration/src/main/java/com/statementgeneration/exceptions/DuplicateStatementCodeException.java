package com.statementgeneration.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateStatementCodeException extends RuntimeException{
	
	private HttpStatus httpStatus;
	 public DuplicateStatementCodeException(String message, HttpStatus httpStatus) {
	        super(message);
	        this.httpStatus=httpStatus;
	    }
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	

}
