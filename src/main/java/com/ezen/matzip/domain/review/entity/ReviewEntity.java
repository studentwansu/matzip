package com.ezen.matzip.domain.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
public class ReviewEntity {

    @Id
    @Column(name = "review_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewCode;

    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @Column(name = "review_date", nullable = false)
    private Date reviewDate;

    @Column(name = "review_report_count", nullable = false)
    private int reviewReportCount;

    @Column(name = "hidden_flag", nullable = false)
    private int hiddenFlag;

    @Column(name = "review_reply")
    private String reviewReply;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "user_code", nullable = false)
    private int userCode;

    @Column(name = "business_code", nullable = false)
    private int businessCode;

    @Column(name = "restaurant_code", nullable = false)
    private int restaurantCode;

    @Column(name = "reservation_code", nullable = false)
    private int reservationCode;
}
