package com.ezen.matzip.domain.review.service;

import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.entity.ReviewEntity;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public ReviewDTO findByRestaurantCode(int restaurantCode){

        ReviewEntity foundReview = reviewRepository.findById(restaurantCode).orElseThrow(IllegalAccessError::new);

        return modelMapper.map(foundReview, ReviewDTO.class);
    }
}
