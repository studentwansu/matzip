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
    private int reportCount;      // 신고당한 횟수
    private boolean banned;       // accountStatus가 1이면 정지 (true), 0이면 활성 (false)
    private List<ReviewDTO> reviewList;  // 작성한 리뷰 목록
}
