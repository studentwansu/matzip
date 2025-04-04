package com.ezen.matzip.domain.review.dto;

import com.ezen.matzip.domain.review.entity.ReviewImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ReviewImageDTO {

    private int reviewImageCode;
    private int reviewCode;
    private String reviewImagePath;
    private String reviewOriginalName;
    private String reviewSaveName;

    public ReviewImageDTO(String reviewImagePath, String reviewOriginalName, String reviewSaveName) {
        this.reviewImagePath = reviewImagePath;
        this.reviewOriginalName = reviewOriginalName;
        this.reviewSaveName = reviewSaveName;
    }

}
