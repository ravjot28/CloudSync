package com.dropbox.exception;

public class InvalidRequestException extends Exception{
	private static final long serialVersionUID = 7906385205614453974L;

	public InvalidRequestException(String message){
		super(message);
	}

}
