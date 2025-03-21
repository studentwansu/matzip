package com.ezen.matzip.domain.user.dto;

import com.ezen.matzip.domain.Role;
import lombok.Data;

@Data
public class BusinessRequestDTO {
    private String restaurantName;  //상호명
    private String id;              //아이디
    private String password;        //비밀번호
    private String question;        //비밀번호 찾기 질문
    private String answer;          //비밀번호 찾기 답변
    private String email;           //이메일
    private String phone;           //전화번호
    private Role role;            //권한
    private String restaurantNumber;//사업자 등록 번호
}


// 사업자회원가입요청
