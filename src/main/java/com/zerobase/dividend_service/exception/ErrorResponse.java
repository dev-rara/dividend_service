package com.zerobase.dividend_service.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

	private int code;
	private String message;
}
