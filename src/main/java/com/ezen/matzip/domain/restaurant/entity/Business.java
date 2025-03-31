package com.ezen.matzip.domain.user.entity;

import com.ezen.matzip.domain.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private Role role;

    @Builder
    public Business(String userId, String password, String phoneNumber, String passwordQuestion, String passwordAnswer, String businessNumber, String restaurantName, String email, Role role) {
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.passwordQuestion = passwordQuestion;
        this.passwordAnswer = passwordAnswer;
        this.businessNumber = businessNumber;
        this.restaurantName = restaurantName;
        this.email = email;
    }
}
