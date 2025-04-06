package com.ezen.matzip.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    // 최소 신고 횟수
    private int reportCountThreshold;
    // 처리되지 않은 리뷰 포함 여부
    private boolean unprocessed;
    // 처리된 리뷰 포함 여부
    private boolean processed;
    // 유저 아이디 (검색용)
    private String userId;
}
