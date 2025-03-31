package com.ezen.matzip.domain.user.entity;

import com.ezen.matzip.domain.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private Integer userCode;

    @Column(name = "user_id",nullable = false,unique = true)
    private String userId;              //아이디
    @Column(name = "password",nullable = false)
    private String password;            //비밀번호
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;         //전화번호
    @Column(name = "password_question",nullable = false)
    private String passwordQuestion;    //비밀번호찾기질문
    @Column(name = "password_answer",nullable = false)
    private String passwordAnswer;      //비밀번호찾기답변
    @Column(name = "name",nullable = false)
    private String name;                //이름
    @Column(name = "nationality",nullable = false)
    private String nationality;         //국가
    @Builder.Default
    @Column(name = "is_vegan", nullable = false)
    private Integer isVegan = 0;               //비건여부
    @Column(name = "category_code",nullable = false)
    private Integer categoryCode;           //선호음식취향
    @Column(name = "user_report_count", nullable = false)
    private Integer userReportCount;        //신고당한횟수
    @Column(name = "account_status")
    private Integer accountStatus;          //정지여부
    @Column(name = "email",nullable = false,unique = true)
    private String email;               //이메일
    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private Role role;                  //역할
}

