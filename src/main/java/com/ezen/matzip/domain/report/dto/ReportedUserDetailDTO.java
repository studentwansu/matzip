package com.ezen.matzip.domain.report.dto;

import com.ezen.matzip.domain.review.dto.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedUserDetailDTO {
    private int userCode;
    private String userId;
    private int reportCount;
    private boolean banned;
    private List<ReviewDTO> reviewList;
}
