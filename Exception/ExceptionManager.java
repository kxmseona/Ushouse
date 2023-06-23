package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {

	
	@ExceptionHandler(AppException.class)
	public ResponseEntity<?> AppExceptionHandler(AppException e){
		return ResponseEntity.status(e.getErrorCode().getHttpStatus())
				.body(e.getErrorCode().name() + " " + e.getMessage());
	}
	
	
	
	
	
	
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(e.getMessage());
		// e - error 에러메세지와 함께 오류상태 반환 (409 Conflict - 이 응답은 요청이 현재서버의 상태와 충돌될때 보냅니다.)
		// db에 사용자가 입력한 정보가 있을때 충돌이 일어난다. 즉, 회원가입 불가. 즉, 클라이언트가 회원가입정보를 다시 입력해야한다.
	}
	
}
