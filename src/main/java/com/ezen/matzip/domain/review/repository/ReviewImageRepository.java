package com.ezen.matzip.domain.review.repository;

import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Integer> {
    List<ReviewImage> findByReviewCode(Integer reviewCode);

    @Query("SELECT Review, i.reviewImagePath FROM Review r JOIN ReviewImage i ON r.reviewCode = i.reviewCode")
    List<Object[]> findReviewImagesByReviewCodes(List<Review> reviewCode);


}
