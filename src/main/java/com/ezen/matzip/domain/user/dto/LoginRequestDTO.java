package com.ezen.matzip.domain.user.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String id;
    private String password;
}

//로그인 요청
