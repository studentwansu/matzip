package com.ezen.matzip.domain.review.repository;

import com.ezen.matzip.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewAnswerRepository extends JpaRepository<Review, Integer> {

    // 특정 사업자(business)의 리뷰 목록 + month 조회
    @Query("SELECT r, r.restaurantCode FROM Review r where r.businessCode = :businessCode AND MONTH(r.reviewDate) = :month")
    List<Object[]> findByBusinessCodeAndReviewDate(@Param("businessCode") int businessCode, @Param("month") int month);

    List<Review> findTop5ReviewByBusinessCodeOrderByReviewDateDesc(int businessCode);

    Optional<Review> findReviewByReviewCode(int reviewCode);

    // 특정 사업자(business)의 리뷰 목록 조회
    @Query("SELECT r, r.restaurantCode, u.userId, u.nationality FROM Review r JOIN User u ON r.userCode = u.userCode WHERE r.businessCode = :businessCode")
    List<Object[]> findByBusinessCodeWithUserId(@Param("businessCode") int businessCode);

}
