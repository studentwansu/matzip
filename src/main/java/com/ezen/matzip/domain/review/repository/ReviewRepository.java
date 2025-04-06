package com.ezen.matzip.domain.review.repository;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // 특정 사용자(userCode)의 리뷰 목록을 조회하는 메서드
    @Query("SELECT r, r.restaurantCode FROM Review r where r.userCode = :userCode")
    List<Object[]> findByUserCode(int userCode);

    Optional<Review> findByReviewCode(int reviewCode);

    List<Review> findByRestaurantCode(@Param("restaurantCode") Restaurant restaurantCode);

    //완수- 신고수 동기화에 필요
    // 특정 유저(userCode)가 작성한 리뷰들의 신고 수 합계를 반환 (신고가 없으면 0 반환)
    @Query("SELECT COALESCE(SUM(r.reviewReportCount), 0) FROM Review r WHERE r.userCode = :userCode")
    int sumReportCountByUserCode(@Param("userCode") int userCode);

    // 계정 정지 상태 변경 시, 해당 유저의 모든 리뷰의 hiddenFlag를 업데이트 (1: 숨김, 0: 노출)
    @Modifying
    @Query("update Review r set r.hiddenFlag = :hiddenFlag where r.userCode = :userCode")
    void updateHiddenFlagByUserCode(@Param("userCode") int userCode, @Param("hiddenFlag") int hiddenFlag);
    //완수 끝
}
