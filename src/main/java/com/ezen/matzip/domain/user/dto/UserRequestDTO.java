package com.ezen.matzip.domain.user.dto;


import lombok.Data;

@Data
public class UserRequestDTO {
    private int userCode;  //사용자가 직접 입력한 아이디
    private String userId;    //일반회원 이름
    private String password;    //비밀번호
    private String phoneNumber;
    private String passwordQuestion;
    private String passwordAnswer;
    private String name;
    private String nationality;
    private Boolean isVegan;
    private int categoryCode;
    private int userReportCount;
    private Boolean accountStatus;
}


