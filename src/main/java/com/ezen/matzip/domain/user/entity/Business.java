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
public class Business {

    @Id
    private String id;  //사업자가 직접 입력한 아이디

    private String restaurantName;  //상호명
    private String password;        //비밀번호
    private String question;        //비밀번호 찾기 질문
    private String answer;          //비밀번호 찾기 답변
    private String email;           //이메일
    private String phone;           //전화번호
    private Role role;            //권한
    private String restaurantNumber;//사업자 등록 번호
}
