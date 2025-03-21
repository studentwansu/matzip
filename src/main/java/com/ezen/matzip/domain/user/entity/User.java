package com.ezen.matzip.domain.user.entity;

import com.ezen.matzip.domain.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;  //사용자가 직접 입력한 아이디

    private String username;    //일반회원 이름
    private String password;    //비밀번호
    private String question;    //비밀번호 찾기 질문
    private String answer;      //비밀번호 찾기 답변
    private String email;       //이메일
    private String phone;       //전화번호
    private String country;     //국가
    private Role role;        //권한
    private String preference;  //선호 음식 취향
    private Boolean vegan;      //채식 여부
}

//사용자 엔티티(Role 포함)