package com.statementgeneration.util;


import lombok.ToString;

@ToString
public class ApiResponse {

	private int status;
	private String message;
	private Object data;
	private boolean statementExistance;

	public boolean isStatementExistance() {
		return statementExistance;
	}

	public void setStatementExistance(boolean statementExistance) {
		this.statementExistance = statementExistance;
	}

	public ApiResponse() {
		super();
	}

	public ApiResponse(int status, String message, Object data, boolean statementExistance) {

		this.status = status;
		this.message = message;
		this.data = data;
		this.statementExistance=statementExistance;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
