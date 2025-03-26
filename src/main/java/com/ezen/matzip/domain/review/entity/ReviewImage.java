package com.ezen.matzip.domain.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {

    @Id
    private int reviewImageCode;
    private int reviewCode;
    private String reviewImagePath;
    private String reviewOriginalName;
    private String reviewSaveName;

    @Builder
    public ReviewImage(int reviewImageCode, int reviewCode, String reviewImagePath, String reviewOriginalName, String reviewSaveName) {
        this.reviewCode = reviewCode;
        this.reviewImagePath = reviewImagePath;
        this.reviewOriginalName = reviewOriginalName;
        this.reviewSaveName = reviewSaveName;
    }
}
