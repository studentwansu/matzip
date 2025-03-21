package com.ezen.matzip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //일반회원 중복 아이디 예외처리
    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserIdException(DuplicateUserIdException ex) {
        ErrorResponse errorResponse = new ErrorResponse("USER_ID_CONFLICT", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    //사업자 중복 아이디 예외처리
    @ExceptionHandler(DuplicateBusinessIdException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateBusinessIdException(DuplicateBusinessIdException ex) {
        ErrorResponse errorResponse = new ErrorResponse("BUSINESS_ID_CONFLICT", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    //기타 예상치못한 예외처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", "예상치 못한 오류가 발생했습니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//GlobalExceptionHandler.java → Spring 전역 예외 처리
