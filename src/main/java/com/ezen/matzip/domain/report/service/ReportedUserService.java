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

    public Page<ReportedUserDTO> getReportedUsers(SearchCriteria criteria, int page) {
        List<Integer> accountStatuses = new ArrayList<>();
        if (criteria.isUnprocessed()) {
            accountStatuses.add(0);
        }
        if (criteria.isProcessed()) {
            accountStatuses.add(1);
        }
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
