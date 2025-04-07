package com.ezen.matzip.domain.report.service;

import com.ezen.matzip.domain.report.dto.ReportedUserDTO;
import com.ezen.matzip.domain.report.dto.SearchCriteria;
import com.ezen.matzip.domain.report.repository.ReportedUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportedUserService {

    private final ReportedUserRepository reportedUserRepository;

    public Page<ReportedUserDTO> getReportedUsers(SearchCriteria criteria, int page) {// 계정 상태를 통해 처리 여부를 판단:
        // 0: 처리되지 않은 유저 (계정 활성화됨), 1: 처리된 유저 (계정 정지됨)
        List<Integer> accountStatuses = new ArrayList<>();
        if (criteria.isUnprocessed()) {
            accountStatuses.add(0);
        }
        if (criteria.isProcessed()) {
            accountStatuses.add(1);
        }
        // 둘 다 선택되지 않은 경우 전체(0,1)를 조회
        if (accountStatuses.isEmpty()) {
            accountStatuses.add(0);
            accountStatuses.add(1);
        }
        Pageable pageable = PageRequest.of(page - 1, 10);
        return reportedUserRepository.findReportedUsers(
                criteria.getReportCountThreshold(),
                criteria.getUserId(),
                accountStatuses,
                pageable
        );
    }

    public int countByAccountStatus(int accountStatus){
            return reportedUserRepository.countByAccountStatus(accountStatus);
    }
}
