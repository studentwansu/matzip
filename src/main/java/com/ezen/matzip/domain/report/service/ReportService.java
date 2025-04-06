package com.ezen.matzip.domain.report.service;

import com.ezen.matzip.domain.report.dto.ReportedReviewDTO;
import com.ezen.matzip.domain.report.dto.SearchCriteria;
import com.ezen.matzip.domain.report.repository.ReportRepository;
import com.ezen.matzip.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public Page<ReportedReviewDTO> getReportedReviews(SearchCriteria criteria, int page) {
        List<Integer> hiddenFlags = new ArrayList<>();
        if (criteria.isUnprocessed()) {
            hiddenFlags.add(0);
        }
        if (criteria.isProcessed()) {
            hiddenFlags.add(1);
        }
        if (hiddenFlags.isEmpty()) {
            hiddenFlags.add(0);
            hiddenFlags.add(1);
        }

        Pageable pageable = PageRequest.of(page - 1, 10);
        // 바로 Repository에서 받은 DTO 페이지를 반환
        return reportRepository.findReportedReviewsWithUserId(
                criteria.getReportCountThreshold(),
                hiddenFlags,
                criteria.getUserId(),
                pageable);
    }

    public int countByHiddenFlag(int hiddenFlag) {
        return reportRepository.countByHiddenFlag(hiddenFlag);
    }
}
