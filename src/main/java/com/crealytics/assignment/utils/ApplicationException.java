package com.crealytics.assignment.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1979562073584186174L;
	private String message;

	public ApplicationException(String cause) {
		super();
		this.message = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setCause(String message) {
		this.message = message;
	}
	
	

}
