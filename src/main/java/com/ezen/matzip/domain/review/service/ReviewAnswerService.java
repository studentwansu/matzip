package com.ezen.matzip.domain.review.service;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.repository.ReviewAnswerRepository;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewAnswerService {

    private final ReviewAnswerRepository reviewAnswerRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public ReviewDTO findReviewByReviewCode(int reviewCode) {
        Review found = reviewAnswerRepository.findByReviewCode2(reviewCode);
        ReviewDTO newReviewDTO = new ReviewDTO();
        newReviewDTO.setReviewCode(reviewCode);
        newReviewDTO.setBusinessCode(found.getBusinessCode());
        return newReviewDTO;
    }

    public List<ReviewDTO> findReviewByBusinessCode(int businessCode) {
        List<Object[]> reviews = reviewAnswerRepository.findByBusinessCode(businessCode);

        List<ReviewDTO> result = new ArrayList<>();
        for (Object[] review : reviews) {
            Review e = (Review) review[0];
            Restaurant restaurant = (Restaurant) review[1];
            ReviewDTO dto = new ReviewDTO();
            dto.setUserCode(e.getUserCode());
            dto.setRestaurantName(restaurant);
            dto.setReviewCode(e.getReviewCode());
            dto.setReviewDate(e.getReviewDate());
            dto.setReviewContent(e.getReviewContent());
            dto.setReviewReply(e.getReviewReply());
            dto.setRating(e.getRating());

            result.add(dto);
        }
        return result;
    }

    public List<ReviewDTO> findReviewByBusinessCodeAndMonth(int businessCode, int month) {
        List<Object[]> reviews = reviewAnswerRepository.findByBusinessCodeAndReviewDate(businessCode, month);

        List<ReviewDTO> result = new ArrayList<>();
        for (Object[] review : reviews) {
            Review e = (Review) review[0];
            Restaurant restaurant = (Restaurant) review[1];
            ReviewDTO dto = new ReviewDTO();
            dto.setUserCode(e.getUserCode());
            dto.setRestaurantName(restaurant);
            dto.setReviewCode(e.getReviewCode());
            dto.setReviewDate(e.getReviewDate());
            dto.setReviewContent(e.getReviewContent());
            dto.setReviewReply(e.getReviewReply());
            dto.setRating(e.getRating());

            result.add(dto);
        }
        return result;
    }

    public List<Review> getRecentReview(int businessCode) {
        return reviewAnswerRepository.findTop5ReviewByBusinessCodeOrderByReviewDateDesc(businessCode);
    }


    @Transactional
    public void modifyAnswer(ReviewDTO dto) {
        Review review = reviewAnswerRepository.findByReviewCode(dto.getReviewCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰 없음"));
        System.out.println("수정 요청된 리뷰 코드: " + dto.getReviewCode());

        review.modifyAnswer(dto.getReviewReply());
        reviewAnswerRepository.save(review);
    }


    @Transactional
    public void saveReply(int reviewCode, String replyContent){
        Review review = reviewAnswerRepository.findById(reviewCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다."));

        review.writeReply(replyContent);
        reviewAnswerRepository.save(review);
    }



}
