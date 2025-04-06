package com.ezen.matzip.domain.report.repository;

import com.ezen.matzip.domain.report.dto.ReportedReviewDTO;
import com.ezen.matzip.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Review, Integer> {

    @Query("select new com.ezen.matzip.domain.report.dto.ReportedReviewDTO(" +
            "r.reviewCode, " +
            "r.hiddenFlag, " +
            "r.restaurantCode.restaurantCode, " +  // 👈 여기!
            "usr.userId, " +
            "r.reviewReportCount, " +
            "r.reviewContent, " +
            "r.reviewDate) " +
            "from Review r, com.ezen.matzip.domain.user.entity.User usr " +
            "where r.userCode = usr.userCode " +
            "and r.reviewReportCount >= :reportCountThreshold " +
            "and r.hiddenFlag in :hiddenFlags " +
            "and (:userId = '' or usr.userId like concat('%', :userId, '%'))")
    Page<ReportedReviewDTO> findReportedReviewsWithUserId(
            @Param("reportCountThreshold") int reportCountThreshold,
            @Param("hiddenFlags") List<Integer> hiddenFlags,
            @Param("userId") String userId,
            Pageable pageable
    );

    @Query("select new com.ezen.matzip.domain.report.dto.ReportedReviewDTO(" +
            "r.reviewCode, " +
            "r.hiddenFlag, " +
            "r.restaurantCode.restaurantCode, " +
            "usr.userId, " +
            "r.reviewReportCount, " +
            "r.reviewContent, " +
            "r.reviewDate) " +
            "from Review r, com.ezen.matzip.domain.user.entity.User usr " +
            "where r.userCode = usr.userCode " +
            "and r.reviewCode = :reviewCode")
    ReportedReviewDTO findReviewDetail(@Param("reviewCode") int reviewCode);

    // 처리 상태별 카운트
    int countByHiddenFlag(int hiddenFlag);
}
