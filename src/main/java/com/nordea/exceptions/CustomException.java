package com.nordea.exceptions;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 3853784562569035374L;

	public CustomException(String message) {
		super(message);
	}

}
