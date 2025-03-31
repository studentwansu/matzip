package com.ezen.matzip.domain.review.repository;

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

//    List<ReviewImage> findReviewImagesByReviewCode(Review reviewCode);

//    @Query("SELECT i FROM ReviewImage i JOIN i.review.reviewCode ON Review.reviewCode WHERE i.review.reviewCode = :reviewCode")
//    List<ReviewImage> findReviewImagesByReviewCode(@Param("reviewCode") int reviewCode);
    @Query("SELECT i FROM ReviewImage i WHERE i.reviewCode.reviewCode = :reviewCode")
    List<ReviewImage> findReviewImagesByReviewCode(@Param("reviewCode") int reviewCode);

//    ReviewImage findReviewImageByReviewCode(Review reviewCode);

//    @Query("SELECT ReviewImage FROM ReviewImage i JOIN i.review.reviewCode ON Review.reviewCode WHERE i.review.reviewCode = :reviewCode")
//    ReviewImage findReviewImageByReviewImageCode(int reviewCode);

//    @Query("SELECT Review, i.reviewImagePath FROM Review r JOIN ReviewImage i ON r.reviewCode = i.review.reviewCode")
//    List<Object[]> findReviewImagesByReviewCodes(List<Review> reviewCode);


}
