package com.ezen.matzip.domain.review.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReviewImageDTO {

    private int reviewImageCode;
    private int reviewCode;
    private String reviewImagePath;
    private String reviewOriginalName;
    private String reviewSaveName;
}
