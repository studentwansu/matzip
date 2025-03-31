package com.ezen.matzip.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "business")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_code")
    private int businessCode;
    private String userId;
    private String password;
    private String phoneNumber;
    private String passwordQuestion;
    private String passwordAnswer;
    private String businessNumber;
    private String restaurantName;
    private String email;
    private int role;
}
