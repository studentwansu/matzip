package com.ezen.matzip.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ReportedReviewDTO {

    private int reviewCode;
    private int hiddenFlag;
    private int restaurantCode; // Restaurant 엔티티의 코드값 (예: getRestaurantCode())
    private String userId;         // User 엔티티의 userId (연관관계를 통해 가져옴)
    private int reportCount;
    private String reviewContent;
    private Date reviewDate;

    // JPQL 쿼리에서 요구하는 생성자
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
