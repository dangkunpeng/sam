package com.sam.sam_parents.exceptions;

import java.util.List;

public class AjaxErrorException extends RuntimeException {

	private List<String> errorList;
	
	public AjaxErrorException(List<String> errors) {
		this.errorList = errors;
	}

	public List<String> getErrorList() {
		return errorList;
	}

}
