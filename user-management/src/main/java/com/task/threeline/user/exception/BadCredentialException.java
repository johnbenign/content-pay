package com.task.threeline.user.exception;

public class BadCredentialException extends RuntimeException {

	public BadCredentialException(String message) {
		super(message);
	}

	public BadCredentialException() {
		super("Invalid Login credential");
	}
}