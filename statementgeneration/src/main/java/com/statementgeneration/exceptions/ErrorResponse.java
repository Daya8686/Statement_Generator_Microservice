package com.statementgeneration.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ErrorResponse {
	private int status;
	private String message;
	private LocalDateTime timestamp;
	private Map<String, String> errors; // Field errors with details

	public ErrorResponse() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	

}
