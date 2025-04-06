package com.ezen.matzip.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedUserDTO {
    private String userId;
    private int userReportCount;
    // processed: true면 관리자에 의해 처리(계정 정지됨), false면 처리되지 않음
    private boolean processed;
    // accountStatus: 0 = 계정 활성화됨, 1 = 계정 정지됨
    private int accountStatus;
}
