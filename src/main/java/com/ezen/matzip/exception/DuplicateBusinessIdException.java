package com.ezen.matzip.exception;

public class DuplicateBusinessIdException extends RuntimeException {

    public DuplicateBusinessIdException(String message) {
        super(message);
    }
}

//중복 사업자 아이디 예외처리
