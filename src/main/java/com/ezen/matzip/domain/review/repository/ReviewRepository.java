package com.ezen.matzip.domain.review.repository;

import com.ezen.matzip.domain.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    // 특정 사용자(userCode)의 리뷰 목록을 조회하는 메서드
    List<ReviewEntity> findByUserCode(int userCode);
}
