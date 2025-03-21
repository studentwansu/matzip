package com.ezen.matzip.domain.user.dto;

import com.ezen.matzip.domain.Role;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String userName;    //이름
    private String id;          //아이디
    private String password;    //비밀번호
    private String question;    //비밀번호 찾기 질문
    private String answer;      //비밀번호 찾기 답변
    private String email;       //이메일
    private String phone;       //전화번호
    private String country;     //국가
    private Role role;        //권한
    private String preference;  //선호 음식 취향
    private Boolean vegan;       //비건 여부
}

//일반회원 회원가입 요청
