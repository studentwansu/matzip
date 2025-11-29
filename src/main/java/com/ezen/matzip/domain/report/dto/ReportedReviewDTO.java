package com.ezen.matzip.domain.report.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ReportedReviewDTO {

    private int reviewCode;
    private int hiddenFlag;
    private int restaurantCode;
    private String userId;
    private int reportCount;
    private String reviewContent;
    private Date reviewDate;

    public ReportedReviewDTO(int reviewCode, int hiddenFlag, int restaurantCode, String userId, int reportCount, String reviewContent, Date reviewDate) {
        this.reviewCode = reviewCode;
        this.hiddenFlag = hiddenFlag;
        this.restaurantCode = restaurantCode;
        this.userId = userId;
        this.reportCount = reportCount;
        this.reviewContent = reviewContent;
        this.reviewDate = reviewDate;
    }
}
