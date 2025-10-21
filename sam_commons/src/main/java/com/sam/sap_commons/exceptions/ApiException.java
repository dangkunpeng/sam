package com.sam.sap_commons.exceptions;

@SuppressWarnings("serial")
public class ApiException extends RuntimeException {

	private int errorcode;
	private String message;

	public ApiException(int errorCode, String message) {
		this.errorcode = errorCode;
		this.message = message;
	}
	
	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public enum ApiCode {

		API_CODE_SYSTEM_ERROR(9999),
		API_CODE_NO_DATA(8888);
		
		int code;
		ApiCode(int code) {
			this.code = code;
		}
	}
}
