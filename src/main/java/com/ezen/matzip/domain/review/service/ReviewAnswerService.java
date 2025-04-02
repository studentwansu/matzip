package com.ezen.matzip.domain.review.service;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.repository.ReviewAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewAnswerService {

    private final ReviewAnswerRepository reviewAnswerRepository;
    private final ModelMapper modelMapper;

//    public List<ReviewDTO> findReviewByBusinessCode(int businessCode) {
//        List<Review> reviews = reviewAnswerRepository.findByBusinessCode(businessCode);
//
//        return reviews.stream().map(review -> modelMapper.map(review, ReviewDTO.class)).toList();
//    }

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




}
