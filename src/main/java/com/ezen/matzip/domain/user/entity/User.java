package com.ezen.matzip.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private int userCode;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Column(name = "password_question", nullable = false)
    private String passwordQuestion;
    @Column(name = "password_answer", nullable = false)
    private String passwordAnswer;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "nationality", nullable = false)
    private String nationality;
    @Column(name = "is_vegan", nullable = false)
    private Boolean isVegan;
    @Column(name = "category_code")
    private int categoryCode;
    @Column(name = "user_report_count", nullable = false)
    private int userReportCount;
    @Column(name = "account_status")
    private Boolean accountStatus;
}

