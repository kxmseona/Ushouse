package com.example.demo.Exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	// 사용자가 입력한 memberEmail이 이중 충돌 났을때 (즉, 이미 db에 존재할때)
	MEMBEREMAIL_DUPLICATED(HttpStatus.CONFLICT, ""),
	
	MEMBEREMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, ""),
	
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "");
	
	private HttpStatus httpStatus;
	private String message;

}
