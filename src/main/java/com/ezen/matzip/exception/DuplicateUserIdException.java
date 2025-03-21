package com.ezen.matzip.exception;

public class DuplicateUserIdException extends RuntimeException{

    public DuplicateUserIdException(String message) {
        super(message);
    }
}

//중복 일반회원 아이디 중복 예외처리
