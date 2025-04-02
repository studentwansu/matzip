package com.ezen.matzip.domain.review.repository;

import com.ezen.matzip.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewAnswerRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r, r.restaurantCode FROM Review r where r.businessCode = :businessCode")
    List<Object[]> findByBusinessCode(int businessCode);

    @Query("SELECT r, r.restaurantCode FROM Review r where r.businessCode = :businessCode AND MONTH(r.reviewDate) = :month")
    List<Object[]> findByBusinessCodeAndReviewDate(@Param("businessCode") int businessCode, @Param("month") int month);
}
