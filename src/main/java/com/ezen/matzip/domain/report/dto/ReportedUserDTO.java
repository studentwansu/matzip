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
    private boolean processed;
    private int accountStatus;
}
