package com.ezen.matzip.domain.report.service;

import com.ezen.matzip.domain.report.dto.ReportedReviewDTO;
import com.ezen.matzip.domain.report.dto.SearchCriteria;
import com.ezen.matzip.domain.report.repository.ReportRepository;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
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
    // ReviewRepository를 이용하여 엔티티 업데이트
    private final ReviewRepository reviewRepository;

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

    public ReportedReviewDTO getReportedReviewDetail(int reviewCode) {
        return reportRepository.findReviewDetail(reviewCode);
    }

    public int countByHiddenFlag(int hiddenFlag) {
//        return reportRepository.countByHiddenFlag(hiddenFlag);
        // 처리되지 않은 리뷰는 신고 수가 최소 1 이상인 것만 세도록 minReportCount를 1로 지정
        return reportRepository.countByHiddenFlagAndMinReportCount(hiddenFlag, 1);
    }

    @Transactional
    public void toggleHiddenStatus(int reviewCode) {
        ReportedReviewDTO reviewDTO = reportRepository.findReviewDetail(reviewCode);
        int newHiddenFlag = reviewDTO.getHiddenFlag() == 0 ? 1 : 0;
        Review review = reviewRepository.findById(reviewCode).orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
        review.setHiddenFlag(newHiddenFlag);
        reviewRepository.save(review);
    }
}
