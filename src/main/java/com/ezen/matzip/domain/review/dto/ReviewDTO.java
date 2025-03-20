package com.ezen.matzip.domain.review.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class ReviewDTO {

    private int reviewCode;
    private String reviewContent;
    private Date reviewDate;
    private int reviewReportCount;
    private int hiddenFlag;
    private String reviewReply;
    private int rating;
    private int userCode;
    private int businessCode;
    private int restaurantCode;
    private int reservationCode;

}
