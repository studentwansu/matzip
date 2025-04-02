package com.ezen.matzip.domain.review.service;

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

    public List<ReviewDTO> findReviewByBusinessCode(int businessCode) {
        List<Review> reviews = reviewAnswerRepository.findByBusinessCode(businessCode);

        return reviews.stream().map(review -> modelMapper.map(review, ReviewDTO.class)).toList();
    }

}
