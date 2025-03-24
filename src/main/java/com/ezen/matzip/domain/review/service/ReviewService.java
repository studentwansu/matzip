package com.ezen.matzip.domain.review.service;

import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public List<ReviewDTO> findReviewByUserCode(int userCode) {

        List<Review> reviewList = reviewRepository.findByUserCode(userCode);

        return reviewList.stream().map(entity -> modelMapper.map(entity, ReviewDTO.class)).toList();
    }
}
