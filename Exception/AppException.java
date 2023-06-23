package com.example.demo.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {
	
	// 어떤상황에서 exception(예외처리)가 났다를 알려주기 위해 
	private ErrorCode errorCode;
	private String message;

}
