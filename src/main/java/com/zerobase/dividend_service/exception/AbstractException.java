package com.zerobase.dividend_service.exception;

public abstract class AbstractException extends RuntimeException{

	abstract public int getStatusCode();
	abstract public String getMessage();
}
