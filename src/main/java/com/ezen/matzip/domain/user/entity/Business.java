package com.ezen.matzip.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "business")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_code")
    private int businessCode;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "password_question", nullable = false)
    private String passwordQuestion;
    @Column(name = "password_answer", nullable = false)
    private String passwordAnswer;
    @Column(name = "business_number", nullable = false, unique = true)
    private String businessNumber;
    @Column(name = "restaurant_name", unique = true)
    private String restaurantName;
}
