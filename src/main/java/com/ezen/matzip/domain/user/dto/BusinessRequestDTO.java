package com.ezen.matzip.domain.user.dto;


import lombok.Data;

@Data
public class BusinessRequestDTO {
    private int businessCode;
    private String userId;
    private String password;
    private String phoneNumber;
    private String passwordQuestion;
    private String passwordAnswer;
    private String businessNumber;
    private String retaurantName;
}



