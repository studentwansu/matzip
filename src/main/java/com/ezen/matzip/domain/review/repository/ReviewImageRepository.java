package com.ezen.matzip.domain.review.repository;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Integer> {

   @Query("SELECT i FROM ReviewImage i WHERE i.reviewCode.reviewCode = :reviewCode")
    List<ReviewImage> findReviewImagesByReviewCode(@Param("reviewCode") int reviewCode);

    @Query("SELECT i FROM ReviewImage i WHERE i.reviewCode.reviewCode = :reviewCode")
    List<ReviewImage> findByReviewCode(@Param("reviewCode") int reviewCode);


}
