package com.zerobase.dividend_service.exception.impl;

import com.zerobase.dividend_service.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class NotMatchPasswordException extends AbstractException {

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}

	@Override
	public String getMessage() {
		return "비밀번호가 일치하지 않습니다.";
	}
}